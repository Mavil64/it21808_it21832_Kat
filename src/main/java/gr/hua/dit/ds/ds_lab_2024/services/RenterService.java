package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.RenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RenterService
{
    private RenterRepository renterRepository;


    public RenterService(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }
    @Transactional
    public List<Renter> getRenters()
    {
        return renterRepository.findAll();
    }

    @Transactional
    public Renter getRenter(Integer id)
    {
        return renterRepository.findById(id).get();
    }

    @Transactional
    public void saveRenter(Renter renter)
    {
        renterRepository.save(renter);
    }
}
