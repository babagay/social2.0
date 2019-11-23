package social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController
{
    // @RequestMapping(value = "/home", method = RequestMethod.GET)
    @GetMapping({"/","/home"})
    public String homePage(Model model){
//        ModelAndView mv = new ModelAndView();
//        return mv;
        String name = "Foo";
        model.addAttribute("title",name);

        return "home";
    }
}
