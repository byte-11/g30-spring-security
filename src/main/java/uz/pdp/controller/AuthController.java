package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "errorMessage") String error){
        ModelAndView model = new ModelAndView();
        model.addObject("errorMessage", error);
        model.setViewName("login");
        return model;
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }
}
