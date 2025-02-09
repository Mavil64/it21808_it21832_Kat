package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;
import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repository.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Controller
public class UserController {

    private UserService userService;

    private RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        System.out.println("Inside @GetMapping /register");
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        System.out.println("Inside @PostMapping /register");

        if (!isValidEmail(user.getEmail())) {
            model.addAttribute("error", "Invalid email format. Please enter a valid email.");
            return "auth/register"; // Return to registration page with error message
        }

        if (user == null) {
            System.out.println("User is null!");
        } else {
            System.out.println("User: " + user);
        }

        System.out.println(user.getRoleStr());
        System.out.println("test");
        // Call saveUser with the selected roles
        try {
            userService.saveUser(user,  new HashSet<>(Arrays.asList(user.getRoleStr().split("\\s*,\\s*")))); // Convert List<String> to HashSet<String>
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }

        // Redirect to login or success page
        //return "redirect:/login";
        return "index";
    }

    private boolean isValidEmail(String email) {
        // Simple regex for basic email validation
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }



    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "auth/user";
    }

   @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

    //Δεν έχω καταλάβει πως να το κάνω να κάνει update τον ρόλο
    //όπως το έχω κάνει για να κάνει Update τα άλλα στοιχεία για τον authenticated user. χρειάζεται log out log in


    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);

        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails newUserDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authentication.getAuthorities()
        );

        Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "auth/users";

    }




}