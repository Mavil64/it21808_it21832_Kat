package gr.hua.dit.ds.ds_lab_2024.dao;


import gr.hua.dit.ds.ds_lab_2024.entities.RealEstate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RealEstateDAOImpl implements RealEstateDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public List<RealEstate> getEstates() {
        TypedQuery<RealEstate> query = entityManager.createQuery("from RealEstate ", RealEstate.class);
        return query.getResultList();
    }

    @Override
    public RealEstate getEstate(Integer estate_id) {
        return entityManager.find(RealEstate.class, estate_id);
    }

    @Override
    public void saveEstate(RealEstate estate) {
        System.out.println("Estate "+ estate.getEstate_id());
        if (estate.getEstate_id() == null) {
            entityManager.persist(estate);
        } else {
            entityManager.merge(estate);
        }
    }

    @Override
    @Transactional
    public void deleteEstate(Integer estate_id) {
        System.out.println("Deleting estate with id: " + estate_id);
        entityManager.remove(entityManager.find(RealEstate.class, estate_id));
    }
}