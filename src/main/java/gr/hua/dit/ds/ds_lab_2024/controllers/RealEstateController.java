package gr.hua.dit.ds.ds_lab_2024.controllers;
import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;
import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.repository.RealEstateRepository;
import gr.hua.dit.ds.ds_lab_2024.services.RealEstateService;
import gr.hua.dit.ds.ds_lab_2024.services.RenterService;
import gr.hua.dit.ds.ds_lab_2024.services.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/realestate")
public class RealEstateController {

    private RealEstateService  estateServ;
    private TenantService tenantServ;
    private RenterService renterServ;

    public RealEstateController(RealEstateService estateServ, TenantService tenantServ, RenterService renterServ) {
        this.estateServ = estateServ;
        this.tenantServ = tenantServ;
        this.renterServ = renterServ;
    }

    private List<RealEstate> estates = new ArrayList<RealEstate>();



    @GetMapping("/new")
    public String addEstate(Model model)
    {
        RealEstate estate = new RealEstate();
        model.addAttribute("estate", estate);
        return "realestate/realestate";
    }

    @PostMapping("/new")
    public String saveEstate(@ModelAttribute("estate") RealEstate estate, Model model){
        estateServ.saveEstate(estate);
        model.addAttribute("estates", estateServ.getEstates());
        return "realestate/estates";
    }

    @GetMapping("")
    public String showEstates(Model model){
        model.addAttribute("estatelist", estates);
        return "realestate/realestate";
    }

    @GetMapping("/assigntenant/{id}")
    public String showAssignTenanttoEstate(@PathVariable int id, Model model)
    {
        RealEstate estate = estateServ.getEstate(id);
        List<Tenant> tenants = tenantServ.getTenants();
        model.addAttribute("estate", estate);
        model.addAttribute("tenants", tenants);
        return "realestate/assigntenant";
    }

    @PostMapping("/assigntenant/{id}")
    public String assignTenanttoEstate(@PathVariable int id, @RequestParam(value = "tenant", required = true)
    int tenantId, Model model) {
        System.out.println(tenantId);
        Tenant tenant = tenantServ.getTenant(tenantId);
        RealEstate estate = estateServ.getEstate(id);
        estateServ.assignTenanttoEstate(id, tenant);
        return "realestate/assigntenant";
    }

}
