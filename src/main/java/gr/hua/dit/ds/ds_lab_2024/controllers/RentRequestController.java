package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.RentRequest;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.services.RentRequestService;
import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rentrequest")
public class RentRequestController {

    RentRequestService rentRequestService;
    UserService userService;

    public RentRequestController(RentRequestService rentRequestService, UserService userService) {
        this.rentRequestService = rentRequestService;
        this.userService = userService;
    }

    @GetMapping("/requests")
    public String showRequests(Model model)
    {
        List<RentRequest> requests = rentRequestService.getRequestsById(userService.getSpringUserByEmail().getId());
        model.addAttribute("requests", requests);
        return "rentrequest/requestlist";
    }

    @PostMapping("/approve/{id}")
    public String approveRequest(@PathVariable("id") Integer requestId) {
        rentRequestService.updateRequestStatus(requestId, "APPROVED");
        return "redirect:/requests";
    }

    @PostMapping("/deny/{id}")
    public String denyRequest(@PathVariable("id") Integer requestId) {
        rentRequestService.updateRequestStatus(requestId, "DENIED");
        return "redirect:/requests";
    }
}
