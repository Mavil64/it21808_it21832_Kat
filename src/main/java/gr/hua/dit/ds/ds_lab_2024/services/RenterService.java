package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RenterService
{
    private UserRepository userRepository;


    public RenterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public List<User> getRenters()
    {
        return userRepository.findAll();
    }

    @Transactional
    public User getRenter(Long id)
    {
        return userRepository.findById(id).get();
    }

    @Transactional
    public void saveRenter(User user)
    {
        userRepository.save(user);
    }
}
