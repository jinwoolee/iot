package org.iptime.iothome.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.iptime.iothome.domain.User;
import org.iptime.iothome.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
    
    @Resource
    private UserRepository userRepository;
    
    private List<User> userList = new ArrayList<User>();
    
    public UserController(){
        userList.add(new User("brown", "brownpass", "brown", "brown@gmail.com"));
        userList.add(new User("sally", "sallypass", "sally", "sally@gmail.com"));
        userList.add(new User("cony", "conypass", "cony", "cony@gmail.com"));
    }
    
    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }
    
    @PostMapping("/users")
    public String create(User user) {
        //userList.add(user);
        userRepository.save(user);
        
        return "redirect:/user/users";
    }
    
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "/user/list";
    }
    
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "/user/updateForm";
    }
    
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updateUser) {
        User user = userRepository.getOne(id);
        user.update(updateUser);
        userRepository.save(user);
        
        return "redirect:/user/users";
    }
}
