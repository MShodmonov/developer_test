package musobek.shodmonov.developer_test.controllers;

import musobek.shodmonov.developer_test.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        if (!authService.isAuthenticated())
            return "redirect:/index";
        else return "/login";
    }


}
