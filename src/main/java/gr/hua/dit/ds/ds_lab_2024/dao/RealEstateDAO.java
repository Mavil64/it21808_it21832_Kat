package gr.hua.dit.ds.ds_lab_2024.dao;

import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;

import java.util.List;

public interface RealEstateDAO {
    public List<RealEstate> getEstates();
    public RealEstate getEstate(Integer estate_id);
    public void saveEstate(RealEstate estate);
    public void deleteEstate(Integer estate_id);
}