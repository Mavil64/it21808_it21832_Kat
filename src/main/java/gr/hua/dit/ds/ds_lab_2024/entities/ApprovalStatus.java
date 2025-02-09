package gr.hua.dit.ds.ds_lab_2024.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class ApprovalStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public ApprovalStatus() {}

    public ApprovalStatus(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
