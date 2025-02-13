package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tenant")
public class TenantController {

    private UserService userService;

    public TenantController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/tenants")
    public String showTenants(Model model){
        model.addAttribute("tenants", userService.getTenants());
        return "tenant/tenants";
    }
    @GetMapping("/{id}")
    public String showTenant(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("tenant", userService.getUser(id));
            return "tenant/tenant";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @GetMapping
    public String showTenantProfile(Principal principal, Model model, Authentication authentication) {
        // Get username from Spring Security's Principal
        String username = principal.getName();
        System.out.println("DEBUG - User Authorities: " + authentication.getAuthorities());

        System.out.println("test");
        System.out.println(username);
        User authenticatedUser = userService.getSpringUserByEmail();
        System.out.println("User roles: " + authenticatedUser.getRoles());

        model.addAttribute("tenant", authenticatedUser);
        return "tenant/tenant";
    }

    @PostMapping("/delete")
    public String deleteTenant(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        String email = principal.getName();
        userService.deleteUserByEmail(email); // Implement this method in your service
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/logout";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User updatedUser, Principal principal) {
        User savedUser = userService.updateUserDetails(principal.getName(), updatedUser);


        // Update authentication context with new details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails newUserDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getEmail(), // Updated email
                savedUser.getPassword(), // Assuming password isn't changed here
                authentication.getAuthorities() // Keep existing roles
        );

        Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "redirect:/tenant";
    }

    @GetMapping("/edit")
    public String editUserProfile(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "tenant/edit";
    }
}
