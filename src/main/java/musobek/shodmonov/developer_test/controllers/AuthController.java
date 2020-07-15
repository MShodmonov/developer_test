package musobek.shodmonov.developer_test.controllers;

import musobek.shodmonov.developer_test.model.RegisterRequest;
import musobek.shodmonov.developer_test.model.TemplateMessage;
import musobek.shodmonov.developer_test.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        if (authService.isAuthenticated())
            return "redirect:/index";
        else return "login";
    }

    @GetMapping("/register")
    public String getSignInPage(Model model)
    {
        if (!authService.isAuthenticated())
        {
            model.addAttribute("registerRequest",new RegisterRequest());
            model.addAttribute("isRegister",true);
            return "register";
        }
        else return "index";

    }
    @PostMapping("/register")
    public String postLoginPage(@Valid RegisterRequest registerRequest, BindingResult bindingResult, Model model)
    {
        model.addAttribute("isRegister",false);
        if (bindingResult.hasErrors())
        {
            model.addAttribute("message", "please enter information");
            model.addAttribute("registerRequest",registerRequest);
            model.addAttribute("isRegister",true);
            return "register";
        }
        else
        {
            TemplateMessage templateMessage = authService.registerTemporary(registerRequest);
            if (templateMessage.getActionSuccess())
            {
                model.addAttribute("message",templateMessage.getMessage());
                model.addAttribute("isRegister",templateMessage.getActionSuccess());
                return "login";
            }
            else {
                model.addAttribute("message", "please enter information");
                model.addAttribute("isRegister",true);
                return "register";
            }
        }
    }
    @GetMapping("/activate")
    public String activateUser(@RequestParam(name = "activationCode") String activationCode)
    {
        TemplateMessage templateMessage = authService.activateAccount(activationCode);
        if (templateMessage.getActionSuccess())
        {
            return "redirect:/";
        }
        else return "login";
    }


}
