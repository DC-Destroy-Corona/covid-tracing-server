package covid.tracing.epidemiologist;

import covid.tracing.common.enums.Role;
import covid.tracing.common.security.CustomUserDetailsService;
import covid.tracing.common.security.JwtUtil;
import covid.tracing.common.security.LoginDTO;
import covid.tracing.common.security.UserPrincipal;
import covid.tracing.common.service.SignUpService;
import covid.tracing.common.utils.CookieUtil;
import covid.tracing.common.validator.LoginValidator;
import covid.tracing.common.validator.SignUpValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
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


    @GetMapping("/sign-up")
    public String getEpidSignUpForm(Model model) {
        logger.info("\naccept request about get /epid/sign-up");
        model.addAttribute("signUpForm", new EpidemiologistDTO.SignUp());
        return "join";
    }


    @PostMapping("/sign-up")
    public String submitSignUpForm(@Valid @ModelAttribute(name = "signUpForm") EpidemiologistDTO.SignUp signUpForm, Errors errors, Model model) {
        logger.info("\naccept request about post /epid/sign-up");
        logger.info("[epid sign-up form] " + signUpForm.toString());

        signUpValidator.validate(signUpForm, errors);
        if (errors.hasErrors()) {
            return "join";
        }

        signUpService.registerEpidemiologist(signUpForm);
        model.addAttribute("loginForm", new LoginDTO());
        return "login";
    }


    @PostMapping("/login")
    public String loginAsEpidemiologist(@Valid @ModelAttribute(name = "loginForm") LoginDTO loginForm, Errors errors, Model model, HttpServletResponse response) {
        logger.info("\naccept request about /epid/login");
        logger.info("[epid login form] " + loginForm.toString());

        loginValidator.validate(loginForm, Role.EPID, errors);
        if(errors.hasErrors()) {
            return "login";
        }

        final UserPrincipal userPrincipal = userDetailsService.loadEpidemiologistByEmail(loginForm.getEmail());
        final String jwt = jwtUtil.generateToken(userPrincipal, Role.EPID);

        logger.info("id: " + userPrincipal.getId());
        logger.info("token: " + jwt);

        Cookie cookie = CookieUtil.CreateForLogin(jwt);
        response.addCookie(cookie);

        model.addAttribute("name", userPrincipal.getUsername());
        // TODO : 메인 페이지에 배치되어야 할 데이터들 model.addAttribute() 로 추가해줘야함

        return "mainpage";
    }


    @GetMapping("/logout")
    public String logoutAsEpidemiologist(HttpServletResponse response, Model model) {
        Cookie cookie = CookieUtil.CreateForLogout();
        response.addCookie(cookie);
        model.addAttribute("loginForm", new LoginDTO());
        return "login";
    }
}
