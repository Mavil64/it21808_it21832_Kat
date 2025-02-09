package gr.hua.dit.ds.ds_lab_2024.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.hua.dit.ds.ds_lab_2024.entities.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RentRequestRepository extends JpaRepository<ApprovalStatus, Integer> {

    Optional<ApprovalStatus> findByName(String statusname);
    default ApprovalStatus updateOrInsert(ApprovalStatus approvalStatus) {
        ApprovalStatus existing_status = findByName(approvalStatus.getName()).orElse(null);
        if (existing_status != null) {
            return existing_status;
        }
        else {
            return save(approvalStatus);
        }
    }



}