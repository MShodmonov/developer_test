package musobek.shodmonov.developer_test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/","/home","/index","/index.html"})
    public String getHomePage()
    {
        return "index";
    }
}
