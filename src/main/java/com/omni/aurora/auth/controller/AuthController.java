package com.omni.aurora.auth.controller;

import com.omni.aurora.core.model.ApplicationUser;
import com.omni.aurora.core.repository.ApplicationUserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/register")
@RestController
public class AuthController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @ResponseBody
    @GetMapping
    public String register() {
        ApplicationUser user = new ApplicationUser();
        user.setUsername("username");
        user.setRole("ADMIN");
        user.setPassword(this.getEncoder().encode("password"));
        this.applicationUserRepository.save(user);
        return "finish";
    }

    @Bean
    private BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
