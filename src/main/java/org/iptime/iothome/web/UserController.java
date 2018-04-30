package org.iptime.iothome.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        
        if(user == null)
            return "redirect:/user/loginForm";

        else if(user.matchPassword(password)) {
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        }
        else
            return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        
        return "redirect:/";
    }
    
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
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
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/user/loginForm";
        
        if(!sessionUser.matchId(id))
            throw new IllegalStateException("자신만의 정보를 수정할 수 있습니다.");
        
        model.addAttribute("user", userRepository.getOne(id));
        return "/user/updateForm";
    }
    
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updateUser, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/user/loginForm";
        
        if(!sessionUser.matchId(id))
            throw new IllegalStateException("자신만의 정보를 수정할 수 있습니다.");
        
        User user = userRepository.getOne(id);
        user.update(updateUser);
        userRepository.save(user);
        
        return "redirect:/user/users";
    }
}
