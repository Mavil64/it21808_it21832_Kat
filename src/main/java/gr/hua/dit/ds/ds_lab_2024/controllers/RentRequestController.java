package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.RentRequest;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.services.RealEstateService;
import gr.hua.dit.ds.ds_lab_2024.services.RentRequestService;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/rentrequest")
public class RentRequestController {

    RentRequestService rentRequestService;
    RealEstateService estateService;
    UserService userService;

    public RentRequestController(RentRequestService rentRequestService, UserService userService, RealEstateService estateService) {
        this.rentRequestService = rentRequestService;
        this.userService = userService;
        this.estateService = estateService;
    }

    @GetMapping("/requestlist")
    public String showRequests(Model model)
    {
        System.out.println("test" +
                rentRequestService.getRentRequests().toString());
        List<RentRequest> requests = rentRequestService.getRequestsById(userService.getSpringUserByEmail().getId());
        model.addAttribute("requests", requests);
        return "rentrequest/requestlist";
    }

    @PostMapping("/approve/{id}")
    public String approveRequest(@PathVariable("id") Integer requestId) {
        rentRequestService.updateRequestStatus(requestId, "APPROVED");
        return "redirect:/rentrequest/requestlist";
    }

    @PostMapping("/deny/{id}")
    public String denyRequest(@PathVariable("id") Integer requestId) {
        rentRequestService.updateRequestStatus(requestId, "DENIED");
        return "redirect:/rentrequest/requestlist";
    }

    @PostMapping("/makerequest/{id}")
    public String makeRequest(@PathVariable("id") Integer id, Principal principal)
    {
        User tenant = userService.getUserByEmail(principal.getName());
        RentRequest request = new RentRequest("PENDING", estateService.getEstate(id), tenant);
        rentRequestService.saveRentRequest(request);
        return "redirect:/tenant/tenants";
    }

}
