package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.config.security.UserContext;

@Controller
public class UserContextController {
    private final UserContext userContext;

    public UserContextController(UserContext userContext) {
        this.userContext = userContext;
    }

    @GetMapping("/user-context")
    @ResponseBody
    public String userContext(){
        return userContext.toString();
    }
}
