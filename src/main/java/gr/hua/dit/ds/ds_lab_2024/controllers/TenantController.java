package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.repository.TenantRepository;
import gr.hua.dit.ds.ds_lab_2024.services.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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



}
