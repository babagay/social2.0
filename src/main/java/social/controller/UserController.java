package social.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import social.model.User;
import social.repository.UserRepo;
import social.service.SecurityService;
import social.service.UserService;
import social.validator.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registrationView(Model uiModel)
    {
        uiModel.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("userForm") User user,
                           BindingResult result,
                           Model model,
                           Locale locale)
    {
        userValidator.validate(user, result);

        if (result.hasErrors())
        {
            logger.info("Validation errors during registration");

            return "registration";
        }

        userService.add(user);

        // userService.save(user); // another variant

        securityService.autoLogin(user.getUsername(), user.getPassword());

        return "redirect:/home";
    }
}
