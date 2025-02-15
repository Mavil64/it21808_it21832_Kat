package gr.hua.dit.ds.ds_lab_2024.services;

import gr.hua.dit.ds.ds_lab_2024.entities.*;
import gr.hua.dit.ds.ds_lab_2024.repository.RentRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentRequestService {
    private RentRequestRepository rentRequestRepository;


    public RentRequestService(RentRequestRepository rentRequestRepository) {
        this.rentRequestRepository = rentRequestRepository;
    }

    @Transactional
    public List<RentRequest> getRentRequests() {
        return rentRequestRepository.findAll();
    }

    @Transactional
    public RentRequest getRentRequest(Integer id) {
        return rentRequestRepository.findById(id).get();
    }

    @Transactional
    public void saveRentRequest(RentRequest rentRequest) {
        rentRequestRepository.save(rentRequest);
    }

    @Transactional
    public List<RentRequest> getRequestsById(Long renterId) {
        return rentRequestRepository.findAll().stream()
                .filter(rr -> rr.getRealEstate() != null &&
                        rr.getRealEstate().getRenter() != null &&
                        rr.getRealEstate().getRenter().getId().equals(renterId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateRequestStatus(Integer id, String status) {
        Optional<RentRequest> optionalRequest = rentRequestRepository.findById(id);
        System.out.println(optionalRequest.get().getId());
        if (optionalRequest.isPresent()) {
            RentRequest request = optionalRequest.get();
            request.setApprovalStatus(status);
            rentRequestRepository.save(request);
        }
    }

}

