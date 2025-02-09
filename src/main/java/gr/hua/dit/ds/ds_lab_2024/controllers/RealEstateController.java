package gr.hua.dit.ds.ds_lab_2024.controllers;
import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.services.RealEstateService;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/realestate")
public class RealEstateController {

    private RealEstateService estateService;
    private UserService userService;

    public RealEstateController(RealEstateService estateService, UserService userService) {
        this.estateService = estateService;
        this.userService = userService;
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
        estate.setIsapproved(Boolean.FALSE);
        estateService.saveEstate(estate);
        model.addAttribute("estates", estateService.getEstates());
        return "realestate/estates";
    }

    @GetMapping("/estates")
    public String showEstates(Model model){
        model.addAttribute("estates", estateService.getEstates());
        return "realestate/estates";
    }

    @GetMapping("/assigntenant/{id}")
    public String showAssignTenanttoEstate(@PathVariable int id, Model model)
    {
        RealEstate estate = estateService.getEstate(id);
        List<User> tenants = userService.getTenants();
        model.addAttribute("estate", estate);
        model.addAttribute("tenants", tenants);
        return "realestate/assigntenant";
    }

    @PostMapping("/assigntenant/{id}")
    public String assignTenanttoEstate(@PathVariable int id, @RequestParam(value = "tenant", required = true)
    Long tenantId, Model model) {
        System.out.println(tenantId);
        User tenant = userService.getUser(tenantId);
        RealEstate estate = estateService.getEstate(id);
        estateService.assignTenanttoEstate(id, tenant);
        return "realestate/assigntenant";
    }


}
