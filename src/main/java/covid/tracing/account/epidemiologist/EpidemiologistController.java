package covid.tracing.account.epidemiologist;

import covid.tracing.common.domain.ResponseDTO;
import covid.tracing.common.datatype.Role;
import covid.tracing.common.security.CustomUserDetailsService;
import covid.tracing.common.security.JwtUtil;
import covid.tracing.account.LoginDTO;
import covid.tracing.common.security.UserPrincipal;
import covid.tracing.account.SignUpService;
import covid.tracing.account.LoginValidator;
import covid.tracing.account.SignUpValidator;
import covid.tracing.tracing.CovidInfoFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/epid")
public class EpidemiologistController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JwtUtil jwtUtil;
    private SignUpValidator signUpValidator;
    private LoginValidator loginValidator;
    private CustomUserDetailsService userDetailsService;
    private SignUpService signUpService;

    @Autowired
    public EpidemiologistController(JwtUtil jwtUtil,
                                    SignUpValidator signUpValidator, LoginValidator loginValidator,
                                    CustomUserDetailsService customUserDetailsService, SignUpService signUpService) {
        this.jwtUtil = jwtUtil;
        this.signUpValidator = signUpValidator;
        this.loginValidator = loginValidator;
        this.userDetailsService = customUserDetailsService;
        this.signUpService = signUpService;
    }

//    @GetMapping("/sign-up")
//    public String getEpidSignUpForm(Model model) {
//        logger.info("\naccept request about get /epid/sign-up");
//        model.addAttribute("signUpForm", new EpidemiologistDTO.SignUp());
//        return "join";
//    }

    // TODO : MVC -> RESTFUL 방식으로 변경
    @PostMapping("/sign-up")
    public ResponseEntity submitSignUpForm(@RequestBody EpidemiologistDTO.SignUp signUpForm) throws URISyntaxException {
        logger.info("\naccept request about post /epid/sign-up");
        logger.info("[epid sign-up form] " + signUpForm.toString());

//        signUpValidator.validate(signUpForm, errors);
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }

        signUpService.registerEpidemiologist(signUpForm);

        return ResponseEntity.created(new URI("/epid/sign-up"))
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CREATED)
                        .msg("...")
                        .data(null)
                        .build());
    }

    // TODO : MVC -> RESTFUL 방식으로 변경
    @PostMapping("/login")
    public ResponseEntity loginAsEpidemiologist(@RequestBody LoginDTO loginForm) throws URISyntaxException {
//        public ResponseEntity loginAsEpidemiologist(@Valid @RequestBody LoginDTO loginForm, Errors errors) throws URISyntaxException {
//        public String loginAsEpidemiologist(@Valid @ModelAttribute(name = "loginForm") LoginDTO loginForm, Errors errors, Model model, HttpServletResponse response) {
        logger.info("\naccept request about /epid/login");
        logger.info("[epid login form] " + loginForm.toString());

//        loginValidator.validate(loginForm, Role.EPID, errors);
//        if(errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }

        final UserPrincipal userPrincipal = userDetailsService.loadEpidemiologistByEmail(loginForm.getEmail());
        final String jwt = jwtUtil.generateToken(userPrincipal, Role.EPID);

        logger.info("id: " + userPrincipal.getId());
        logger.info("token: " + jwt);

        return ResponseEntity.created(new URI("/epid/login")).body(new Object(){
            public HttpStatus status = HttpStatus.CREATED;
            public String msg = "Success login as epidemiologist";
            public Object data = new Object() {
                public Long epidId = userPrincipal.getId();
                public String token = jwt;
            };
        });
    }
}
