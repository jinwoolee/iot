package org.iptime.iothome;

import javax.annotation.Resource;

import org.iptime.iothome.domain.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Resource
    QuestionRepository questionRepository; 
    
    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        
        return "index";
    }
}
