package covid.tracing.user;

import covid.tracing.common.domain.ResponseDTO;
import covid.tracing.common.enums.Role;
import covid.tracing.common.security.*;
import covid.tracing.common.service.SignUpService;
import covid.tracing.common.validator.LoginValidator;
import covid.tracing.common.validator.SignUpValidator;
import covid.tracing.mail.AuthKeyGenerator;
import covid.tracing.mail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private SignUpValidator signUpValidator;
    private LoginValidator loginValidator;
    private CustomUserDetailsService userDetailsService;
    private EmailService emailService;
    private SignUpService signUpService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          SignUpValidator signUpValidator, LoginValidator loginValidator,
                          CustomUserDetailsService userDetailsService, EmailService emailService, SignUpService signUpService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.signUpValidator = signUpValidator;
        this.loginValidator = loginValidator;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.signUpService = signUpService;
    }


    @PostMapping("/sign-up/email")
    public ResponseEntity signUp(@RequestBody Map<String, Object> body, Errors errors) throws URISyntaxException {
        logger.info("\naccept request about post /user/sign-up/email");

        String email = body.get("email").toString();

        signUpValidator.validate(email, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        String authKey = AuthKeyGenerator.generate();

        signUpService.registerUserAuthentication(authKey, email, Role.USER);
        emailService.sendEmail(authKey, email);

        return ResponseEntity.created(new URI("/sign-up/email"))
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CREATED)
                        .msg("We send email to you for authentication. Please check email and see authentication code")
                        .data(null)
                        .build());
    }


    // 원래는 GET 방식이지만 개발 
    @PostMapping("/sign-up/check-email")
    public ResponseEntity checkEmail(@RequestBody Map<String, Object> body, Errors errors)  {
        logger.info("\naccept request about post /user/sign-up/check-email");

        String authKey = body.get("authKey").toString();
        String email = body.get("email").toString();

        logger.info("accept request for user sign-up (" + email + ")");

        signUpValidator.validate(authKey, email, Role.USER, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok().body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .msg("Your email has been verified. Please submit your information")
                        .data(null)
                        .build());
    }



    @PostMapping("/sign-up")
    public ResponseEntity submitSignUpForm(@Valid @RequestBody UserDTO.SignUp signUp, Errors errors) throws URISyntaxException {
        logger.info("\naccept request about post /user/sign-up");

        // validate : 사용자 정보 디비에 똑같은 이메일을 가진 사용자가 이미 들어가있는지 한번더 검증 필요..
        signUpService.registerUser(signUp);

        // 클라이언트 측에서는 해당 리소스에 대한 응답으로 200 code를 받으면 즉시 사용자 로그인과 관련된 리소스에 접근
        return ResponseEntity.created(new URI("/user/sign-up")).body(new Object() {
            public HttpStatus status = HttpStatus.CREATED;
            public String msg = "Success Sign-Up";
            public Object data = null;
        });
    }


    @PostMapping("/login")
    ResponseEntity loginAsUser(@Valid @RequestBody LoginDTO login, Errors errors) throws URISyntaxException {
        logger.info("\naccept request about post /user/login");
        logger.info("[user login form] " + login.toString());

        loginValidator.validate(login, Role.USER, errors);
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        final UserPrincipal userPrincipal = userDetailsService.loadUserByEmail(login.getEmail());
        final String jwt = jwtUtil.generateToken(userPrincipal, Role.USER);

        logger.info("id: " + userPrincipal.getId());
        logger.info("token: " + jwt);

        return ResponseEntity.created(new URI("/user/login")).body(new Object() {
            public HttpStatus status = HttpStatus.CREATED;
            public String msg = "Success login";
            public Object data = new Object() {
                public Long userId = userPrincipal.getId();
                public String token = jwt;
            };
        });
    }


    @GetMapping("/test")
    public String testResouceWithJwt() {
        return "string";
    }
}
