package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.RealEstateRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RealEstateService
{
    private RealEstateRepository realEstateRepository;


    public RealEstateService(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }
    @Transactional
    public List<RealEstate> getEstates()
    {
        return realEstateRepository.findAll();
    }

    @Transactional
    public RealEstate getEstate(Integer id)
    {
        return realEstateRepository.findById(id).get();
    }

    @Transactional
    public void saveEstate(RealEstate estate)
    {
        realEstateRepository.save(estate);
    }

    @Transactional
    public void assignTenanttoEstate(int id, User tenant)
    {
        RealEstate estate = realEstateRepository.findById(id).get();
        estate.setTenant(tenant);
        saveEstate(estate);
        System.out.println(estate.getTenant().toString());
    }

}
