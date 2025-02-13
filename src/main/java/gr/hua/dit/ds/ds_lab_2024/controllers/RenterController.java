package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.RentRequest;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.services.RentRequestService;
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
import java.util.List;

@Controller
@RequestMapping("/renter")
public class RenterController {
    private UserService userService;
    private RentRequestService rentRequestService;

    public RenterController(UserService userService, RentRequestService rentRequestService) {
        this.userService = userService;
        this.rentRequestService = rentRequestService;
    }

    @GetMapping("/tenants")
    public String showRenters(Model model){
        model.addAttribute("renters", userService.getRenters());
        return "renter/renters";
    }

    @GetMapping("/{id}")
    public String showRenter(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("renter", userService.getUser(id));
            return "renter/renter";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @GetMapping
    public String showRenterProfile(Principal principal, Model model, Authentication authentication) {
        String username = principal.getName();
        User authenticatedUser = userService.getSpringUserByEmail();
        System.out.println("User roles: " + authenticatedUser.getRoles());
        model.addAttribute("renter", authenticatedUser);
        return "renter/renter";
    }

    @PostMapping("/delete")
    public String deleteRenter(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        String email = principal.getName();
        userService.deleteUserByEmail(email);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/logout";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User updatedUser, Principal principal) {
        User savedUser = userService.updateUserDetails(principal.getName(), updatedUser);



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails newUserDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getEmail(),
                savedUser.getPassword(),
                authentication.getAuthorities()
        );

        Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "redirect:/renter";
    }

    @GetMapping("/edit")
    public String editUserProfile(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "renter/edit";
    }

    @GetMapping("/requests")
    public String showRequests(Model model)
    {
        List<RentRequest> requests = rentRequestService.getRequestsById(userService.getSpringUserByEmail().getId());
        model.addAttribute("requests", requests);
        return "rentrequest/requestlist";
    }

}
