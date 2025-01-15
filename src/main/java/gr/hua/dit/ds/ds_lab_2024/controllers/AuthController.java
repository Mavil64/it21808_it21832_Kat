package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {


    RoleRepository roleRepository;

    public AuthController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setup() {
      Role role_tenant = new Role("ROLE_TENANT");
      Role role_renter = new Role("ROLE_RENTER");
      Role role_admin = new Role("ROLE_ADMIN");

      roleRepository.updateOrInsert(role_tenant);
      roleRepository.updateOrInsert(role_renter);
      roleRepository.updateOrInsert(role_admin);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
