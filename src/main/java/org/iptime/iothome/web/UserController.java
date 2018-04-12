package org.iptime.iothome.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    
    private List<User> userList = new ArrayList<User>();
    
    public UserController(){
        userList.add(new User("brown", "brownpass", "brown", "brown@gmail.com"));
        userList.add(new User("sally", "sallypass", "sally", "sally@gmail.com"));
        userList.add(new User("cony", "conypass", "cony", "cony@gmail.com"));
    }
    
    @PostMapping("/create")
    public String create(User user) {
        userList.add(user);
        return "redirect:/list";
    }
    
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("userList", userList);
        return "list";
    }
}
