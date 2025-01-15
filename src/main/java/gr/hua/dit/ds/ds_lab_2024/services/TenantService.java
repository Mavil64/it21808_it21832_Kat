package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TenantService
{
    private TenantRepository tenantRepository;


    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
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
