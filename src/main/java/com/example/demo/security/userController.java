package com.example.demo.security;

import com.example.demo.components.Role;
import com.example.demo.components.User;
import com.example.demo.service.RoleService;
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
import java.util.ArrayList;
import java.util.List;

@Controller @RequestMapping("/registration")
public class userController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private userService userService;
    private RoleService roleService;

    @Autowired
    public userController(userService userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
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
            Role role = roleService.findByRole("USER");
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            User user=new User();
            user.setLogin(signUpRequest.getLogin());
            user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            user.setRoleList(roles);
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
