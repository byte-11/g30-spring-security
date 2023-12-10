package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.UserRegistrationDto;
import uz.pdp.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error){
        ModelAndView model = new ModelAndView();
        model.addObject("errorMessage", error);
        model.setViewName("login");
        return model;
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "register";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute UserRegistrationDto userRegistrationDto){
        userService.save(userRegistrationDto);
        return "redirect:/home";
    }
}
