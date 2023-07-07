package com.tarun.controllers;

import java.util.Arrays;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tarun.entites.User;
import com.tarun.repositories.UserRepository;
import com.tarun.security.UserDetailsImpl;
import com.tarun.service.UserService;

@Controller
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser( User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
        
   
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }

    
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Set the authentication in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Retrieve the authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return "redirect:/dashboard";
    }
    
    @RequestMapping("/dashboard")
    public String dashboardView(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
 
        String username= userDetails.getUsername();
        User user= userService.findByUsername(username);
        model.addAttribute("role", user.getRole());
    	return "dashboard";
    }
}
