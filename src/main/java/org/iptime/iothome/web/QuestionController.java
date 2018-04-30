package org.iptime.iothome.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.iptime.iothome.domain.Question;
import org.iptime.iothome.domain.QuestionRepository;
import org.iptime.iothome.domain.User;
import org.iptime.iothome.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/questions")
@Controller
public class QuestionController {

    @Resource
    private QuestionRepository questionRepository;
    
    @GetMapping("/form")
    public String form(HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/user/loginForm";
        else
            return "/qna/form";
    }
    
    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        System.out.println("create , title : " + title);
        System.out.println("create , contents : " + contents);
        
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/user/loginForm";
        else{
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            Question question = new Question(sessionUser.getUserId(), title, contents);
            questionRepository.save(question);
            
            return "redirect:/";
        }
    }
}
