package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.RealEstateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService
{
    private RealEstateRepository realEstateRepository;

    public AdminService(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }





    @Transactional
    public String approveRealEstate(int id)
    {
        String prompt = "";
        Optional<RealEstate> estate = realEstateRepository.findById(id);
        if(estate.isPresent()) {
             if (estate.get().getApprovalStatus() == "APPROVED"){
                 prompt += "This Estate is already approved";
            }
             else
             {
                 estate.get().setApprovalStatus("APPROVED");
                 realEstateRepository.save(estate.get());
                 prompt += "Estate Approved!";
             }
        }else{
            prompt += "Entity not found.";
            System.out.println("Entity not found.");
        }
        return prompt;
    }

    @Transactional
    public String denyRealEstate(int id)
    {
        String prompt = "";
        Optional<RealEstate> estate = realEstateRepository.findById(id);
        if(estate.isPresent()) {
            if (estate.get().getApprovalStatus() == "DENIED"){
                prompt += "This Estate is already denied";
            }
            else
            {
                estate.get().setApprovalStatus("DENIED");
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