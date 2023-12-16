package uz.pdp.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.exception.UserNotFoundException;
import uz.pdp.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping()
    public String users(Model model){
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String userInfo(@PathVariable("id") Long id){
        return userService.getById(id).toString();
    }

    /*@ExceptionHandler({UserNotFoundException.class})
    @ResponseBody
    public String handleException(UserNotFoundException e){
        return e.getMessage();
    }*/
}
