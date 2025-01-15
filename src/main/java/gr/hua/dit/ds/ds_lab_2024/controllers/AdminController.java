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

    @GetMapping("/estateapproval")
    public String showApproval(Model model){
        int estateid = 0;
        model.addAttribute("estateid", estateid);
        return "admin/estateapproval";

    }

    @PostMapping("/estateapproval")
    public String setApproval(@ModelAttribute("estateid") int estateid, Model model){
        String prompt = adminServ.approveRealEstate(estateid);
        model.addAttribute("prompt", prompt);
        return "admin/estateapproval";
    }

}
