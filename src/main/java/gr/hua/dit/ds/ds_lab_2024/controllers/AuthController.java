package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repository.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Set;

@Controller
public class AuthController {


    RoleRepository roleRepository;


    UserService userService;

    public AuthController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void setup() {
        Role role_tenant = new Role("ROLE_TENANT");
        Role role_renter = new Role("ROLE_RENTER");
        Role role_admin = new Role("ROLE_ADMIN");

        roleRepository.updateOrInsert(role_tenant);
        roleRepository.updateOrInsert(role_renter);
        roleRepository.updateOrInsert(role_admin);



        //Set<Role> roles = Set.of(new Role("ROLE_ADMIN"), new Role("ROLE_TENANT"), new Role("ROLE_RENTER"));
        if (userService.getUserByEmail("admin@email.com") == null) {
            User admin = new User("admin", "admin@email.com", "password");
            userService.saveUser(admin, Set.of("ROLE_ADMIN", "ROLE_TENANT", "ROLE_RENTER"));
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
