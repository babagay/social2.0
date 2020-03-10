package social.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import social.dto.Credentials;
import social.model.User;
import social.service.UserService;

import javax.validation.Valid;

// TODO: Может, вместо Credentials использовать объект User?
@Controller
public class LoginController
{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public ModelAndView login(Model model)
    {
        logger.debug("Controller");

        return new ModelAndView("login", "credentials", new Credentials());
    }

    // Credentials - form backing object
    // [!] сюда НЕ заходит потко выполнения
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("credentials")
                                Credentials credentials,
                        BindingResult result,
                        ModelMap model)
    {
        User user = new User();
        user.setUsername("test");
        User usr = userService.findByObject(user);


        if (result.hasErrors())
        {
            return "login.error";
        }

        model.addAttribute("userName", credentials.getUserName());
        model.addAttribute("password", credentials.getPassword());


        return "home";
    }

    //        ModelAndView mv = new ModelAndView("forward:/home", model);


}
