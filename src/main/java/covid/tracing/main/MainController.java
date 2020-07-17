package covid.tracing.main;

import covid.tracing.common.security.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MainController() {
    }

    @GetMapping("/")
    public String getLoginPage(Model model) {
        logger.info("\naccept request about get /");
        logger.info("request login page...");
        model.addAttribute("loginForm", new LoginDTO());
        return "login";
    }
}
