package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "estates")
public class RealEstate
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer estate_id;

    @Column
    private String estate_name;

    @Column
    private String estate_type;

    @Column
    private Float rent;

    @Column
    private Float area;

    @Column
    private Integer bedrooms;

    @Column
    private Integer bathrooms;

    @Column
    private Integer built_year;
    @Column
    private String address;

    @Column
    private Boolean isapproved;

    @Column
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")  // This is where the owner_id column is referenced in the estates table
    private User renter;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="tenant_id")
    private User tenant;

    public RealEstate() {

    }

    public Integer getEstate_id() {
        return estate_id;
    }

    public void setEstate_id(Integer estate_id) {
        this.estate_id = estate_id;
    }

    public String getEstate_name() {
        return estate_name;
    }

    public void setEstate_name(String estate_name) {
        this.estate_name = estate_name;
    }

    public String getEstate_type() {
        return estate_type;
    }

    public void setEstate_type(String estate_type) {
        this.estate_type = estate_type;
    }

    public Float getRent() {
        return rent;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getBuilt_year() {
        return built_year;
    }

    public void setBuilt_year(Integer built_year) {
        this.built_year = built_year;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isIsapproved() {
        return isapproved;
    }

    public void setIsapproved(Boolean isapproved) {
        this.isapproved = isapproved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public RealEstate(Integer estate_id, String estate_name, String estate_type, Float rent, Float area, Integer bedrooms, Integer bathrooms, Integer built_year,  String address, String description) {
        this.estate_id = estate_id;
        this.estate_name = estate_name;
        this.estate_type = estate_type;
        this.rent = rent;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.built_year = built_year;
        this.isapproved = false;
        this.description = description;
        this.address = address;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "estate_id=" + estate_id +
                ", estate_name='" + estate_name + '\'' +
                ", estate_type='" + estate_type + '\'' +
                ", rent=" + rent +
                ", area=" + area +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", built_year=" + built_year +
                ", address='" + address + '\'' +
                ", isapproved=" + isapproved +
                ", description='" + description + '\'' +
                ", renter=" + renter +
                ", tenant=" + tenant +
                '}';
    }
}
