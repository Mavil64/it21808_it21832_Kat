package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.RealEstateRepository;
import gr.hua.dit.ds.ds_lab_2024.repository.RenterRepository;
import gr.hua.dit.ds.ds_lab_2024.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService
{
    private RealEstateRepository realEstateRepository;
    private RenterRepository renterRepository;
    private TenantRepository tenantRepository;

    public AdminService(RealEstateRepository realEstateRepository, RenterRepository renterRepository, TenantRepository tenantRepository) {
        this.realEstateRepository = realEstateRepository;
        this.renterRepository = renterRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public String approveRealEstate(int id)
    {
        String prompt = "";
        Optional<RealEstate> estate = realEstateRepository.findById(id);
        if(estate.isPresent()) {
             if (estate.get().isIsapproved()){
                 prompt += "This Estate is already approved";
            }
             else
             {
                 estate.get().setIsapproved(true);
                 realEstateRepository.save(estate.get());
                 prompt += "Estate Approved!";
             }
        }else{
            prompt += "Entity not found.";
            System.out.println("Entity not found.");
        }
        return prompt;
    }
}