package ar.com.enrique.clinicsmanager.repository;


import ar.com.enrique.clinicsmanager.model.PetOwner;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetOwnerRepository extends PagingAndSortingRepository<PetOwner, String>,
        JpaSpecificationExecutor<PetOwner> {

    PetOwner findByName(String name);

}
