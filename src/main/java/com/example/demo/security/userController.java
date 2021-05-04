package com.example.demo.security;

import com.example.demo.components.User;
import com.example.demo.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller @RequestMapping("/registration")
public class userController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private userService userService;

    @Autowired
    public userController(userService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String registration(){
        return "registration";
    }
    @PostMapping
    public String registration(@Valid SignUpRequest signUpRequest, BindingResult result, Model model){
        if (result.hasErrors()) return "registration";
        if (signUpRequest.getPassword().equals(signUpRequest.getRepass())){
            User user=new User();
            user.setLogin(signUpRequest.getLogin());
            user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            try {
                userService.addUser(user);
                return "redirect:/login";
            } catch (UsernameNotFoundException e) {
                model.addAttribute("status","User with this username already exists");
                return "registration";
            }

        }
        else{
            model.addAttribute("status","Password mismatch");
            return "registration";
        }
    }
}
