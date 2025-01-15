package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "tenants")
public class Tenant{

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @MapsId // Indicates this field shares the primary key with 'id'
    @JoinColumn(name = "id") // Foreign key to 'users' table
    private User user;
    @OneToMany(mappedBy = "tenant", cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<RealEstate> estates;

    public Tenant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public List<RealEstate> getEstates() {
        return estates;
    }

    public void setEstates(List<RealEstate> estates) {
        this.estates = estates;
    }

    @Override
    public String toString() {
        return  super.toString() +
                "Tenant{" +
                "estates=" + estates +
                '}';
    }
}
