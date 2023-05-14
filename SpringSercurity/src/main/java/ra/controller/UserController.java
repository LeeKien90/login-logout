package ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {
    @GetMapping("/home")
    public String view(){
        return "index";
    }
    @GetMapping("/pageLogin")
    public String loginUser() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminView(){
        return "adminPage";
    }
    @GetMapping("/user")
    public String userView(){
        return "userPage";
    }
    @GetMapping("/errorPage")
    public String errorView(){
        return "error";
    }


}
