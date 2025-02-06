package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.TenantRepository;
import gr.hua.dit.ds.ds_lab_2024.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TenantService
{
    private TenantRepository tenantRepository;
    private UserRepository userRepository;


    public TenantService(TenantRepository tenantRepository, UserRepository userRepository) {

        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public User getTenantByEmail() {
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("No logged-in user found.");
        }

        // Get the email from the authentication object (this is the 'email' field)
        String email = authentication.getName();  // This will give you the logged-in user's email

        // Fetch the user by email (using the email column)
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email: " + email + " not found!"));
    }

    private boolean isTenant(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_TENANT"));
    }

    @Transactional
    public List<Tenant> getTenants()
    {
        return tenantRepository.findAll();
    }

    @Transactional
    public Tenant getTenant(Integer id)
    {
        return tenantRepository.findById(id).get();
    }



    @Transactional
    public void saveTenant(Tenant tenant)
    {
        tenantRepository.save(tenant);
    }
}
