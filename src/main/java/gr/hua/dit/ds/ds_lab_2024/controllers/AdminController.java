package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;
import gr.hua.dit.ds.ds_lab_2024.repository.RealEstateRepository;
import gr.hua.dit.ds.ds_lab_2024.services.AdminService;
import gr.hua.dit.ds.ds_lab_2024.services.RealEstateService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminServ;

    public AdminController(AdminService adminServ) {
        this.adminServ = adminServ;
    }


    @PostMapping("/approve/{estateid}")
    public String setApproval(@PathVariable Integer estateid, Model model){
        String prompt = adminServ.approveRealEstate(estateid);
        model.addAttribute("prompt", prompt); //We ended up not using this prompt idea too much
        return "redirect:/estates";
    }

    @PostMapping("/deny/{estateid}")
    public String setDenial(@PathVariable Integer estateid, Model model){
        String prompt = adminServ.denyRealEstate(estateid);
        model.addAttribute("prompt", prompt);
        return "redirect:/estates";
    }


}
