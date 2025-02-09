package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.ApprovalStatus;
import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.repository.RentRequestRepository;
import gr.hua.dit.ds.ds_lab_2024.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuthController {


    RoleRepository roleRepository;
    RentRequestRepository rentRequestRepository;

    public AuthController(RoleRepository roleRepository, RentRequestRepository rentRequestRepository) {
        this.roleRepository = roleRepository;
        this.rentRequestRepository = rentRequestRepository;
    }

    @PostConstruct
    public void setup() {
      Role role_tenant = new Role("ROLE_TENANT");
      Role role_renter = new Role("ROLE_RENTER");
      Role role_admin = new Role("ROLE_ADMIN");
      ApprovalStatus approved = new ApprovalStatus("APPROVED");
      ApprovalStatus pending = new ApprovalStatus("PENDING");
      ApprovalStatus denied = new ApprovalStatus("DENIED");
      rentRequestRepository.updateOrInsert(approved);
      rentRequestRepository.updateOrInsert(pending);
      rentRequestRepository.updateOrInsert(denied);
      roleRepository.updateOrInsert(role_tenant);
      roleRepository.updateOrInsert(role_renter);
      roleRepository.updateOrInsert(role_admin);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
