package social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import social.entity.Credentials;

import javax.validation.Valid;

@Controller
public class LoginController
{
    @GetMapping("/login")
    public ModelAndView loginPage(Model model){
        return new ModelAndView("login", "credentials", new Credentials());
    }

    // Credentials - form backing object
    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("credentials") Credentials credentials,
                                BindingResult result,
                                ModelMap model){
        if (result.hasErrors())
            return "login.error";



        model.addAttribute("userName", credentials.getUserName());
        model.addAttribute("password", credentials.getPassword());

        return "home";
    }

    //        ModelAndView mv = new ModelAndView("forward:/home", model);



}
