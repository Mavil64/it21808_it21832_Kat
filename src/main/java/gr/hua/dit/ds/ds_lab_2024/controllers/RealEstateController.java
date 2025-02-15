package gr.hua.dit.ds.ds_lab_2024.controllers;
import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.services.RealEstateService;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public String saveEstate(@ModelAttribute("estate") RealEstate estate, Model model, Principal principal){
        estate = estateService.setStatus(estate, "PENDING");
        estate.setRenter(userService.getUserByEmail(principal.getName()));
        estateService.saveEstate(estate);
        model.addAttribute("estates", estateService.getEstates());
        return "realestate/estates";
    }

    @GetMapping("/estates")
    public String showEstates(Model model){
        model.addAttribute("estates", estateService.getEstates());
        return "realestate/estates";
    }

}
