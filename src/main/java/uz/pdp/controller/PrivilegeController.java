package uz.pdp.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/privilege")
public class PrivilegeController {

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER')")
    public String users(){
        return "users are allowed only";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public String admins(){
        return "admins are allowed only";
    }

    @GetMapping("/managers")
    @PreAuthorize("hasAnyAuthority('MANAGER_CREATE', 'ADMIN_DELETE')")
    public String managers(){
        return "managers are allowed only";
    }
}
