package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repository.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);
        userRepository.deleteById(user.getId());

    }

    @Transactional
    public void saveUser(User user, Set<String> roleNames) {

       try{
        String passwd = user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        user.setPassword(encodedPassword);
        System.out.println(roleNames);
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " is not found."));
            roles.add(role);
        }
        System.out.println("this is the Role set" + roles);
        user.setRoles(roles);
        System.out.println("these are the roles saved in the user object " + user.getRoles().toString());
        userRepository.save(user);
        System.out.println("User saved: " + user.getUsername());}catch(Exception e)
       {
           System.err.println("Exception occurred during saveUser: " + e.getMessage());
           e.printStackTrace();
       }
    }
    @Transactional
    public List<User> getTenants()
    {
        return filterUsersByRole(userRepository.findAll(), "ROLE_TENANT");
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User updateUserDetails(String currentEmail, User updatedUser) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Only update username and email, leave everything else unchanged
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user); // Save the modified user while keeping the ID unchanged
        return user;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByEmail(username);

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + username + " not found!");
        } else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    convertRolesToAuthorities(user.getRoles())
            );
        }
    }

    @Transactional
    public User getSpringUserByEmail() {
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

    private Collection<? extends GrantedAuthority> convertRolesToAuthorities(Set<Role> roles) {
        System.out.println("Converted roles: " + roles);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName()
                ))
                .collect(Collectors.toList());
    }

    public List<User> filterUsersByRole(List<User> users, String role) {
        System.out.println("in filter");
        return users.stream()
                .filter(user -> hasRole(user, role))
                .collect(Collectors.toList());
    }

    private boolean hasRole(User user, String role) {
        switch (role) {
            case "ROLE_TENANT":
                return isTenant(user);
            case "ROLE_RENTER":
                return isRenter(user);
            case "ROLE_ADMIN":
                return isAdmin(user);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    private boolean isTenant(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_TENANT"));
    }
    private boolean isRenter(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_RENTER"));
    }
    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public void updateOrInsertRole(Role role) {
        roleRepository.updateOrInsert(role);
    }
}