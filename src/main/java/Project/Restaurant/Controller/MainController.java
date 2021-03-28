package Project.Restaurant.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/")
    public String menu(Model model)
    {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping(value = "/errorPage")
    public String notPermission(Model model)
    {
        model.addAttribute("header", "Ups...");
        model.addAttribute("message", "Nie masz tutaj dostępu");
        return "errorMessage";
    }

}

