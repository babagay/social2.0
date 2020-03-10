package social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import social.model.User;
import social.repository.UserRepo;
import social.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HomeController
{
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserService userService;

    @GetMapping({"/","/home"})
    public String homePage(Model model, Authentication authentication){

        String authName = authentication.getName();

        model.addAttribute("authName",authName);

        User user = new User();
        user.setUsername("test");
        User usr = userService.findByObject(user);

        return "home";
    }
}
