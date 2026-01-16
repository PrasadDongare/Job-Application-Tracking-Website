package controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/")
    public String index() {
        return "public/index";
    }

    @GetMapping("/login")
    public String loginUser() {
        return "public/login-user";
//        return "test";
    }

    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "public/login-admin";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        // model.addAttribute("registerRequest", new RegisterRequest()); // if you want
        return "public/register";
    }
}