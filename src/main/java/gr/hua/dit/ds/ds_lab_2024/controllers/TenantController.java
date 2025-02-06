package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.TenantRepository;
import gr.hua.dit.ds.ds_lab_2024.services.TenantService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tenant")
public class TenantController {

    private TenantService tenantServ;

    public TenantController(TenantService tenantServ) {
        this.tenantServ = tenantServ;
    }

    private List<Tenant> tenants = new ArrayList<Tenant>();


    @GetMapping//("/tenant")
    public String showTenantProfile(Principal principal, Model model, Authentication authentication) {
        // Get username from Spring Security's Principal
        String username = principal.getName();
        System.out.println("DEBUG - User Authorities: " + authentication.getAuthorities());

        System.out.println("test");
        System.out.println(username);
        // Use TenantService to fetch user data
        User authenticatedUser = tenantServ.getTenantByEmail();
        System.out.println("User roles: " + authenticatedUser.getRoles());

        model.addAttribute("tenant", authenticatedUser);
        return "tenant/tenant";
    }

}
