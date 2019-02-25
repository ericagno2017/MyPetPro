package ar.com.enrique.clinicsmanager.repository;

import ar.com.enrique.clinicsmanager.model.Pet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository  extends PagingAndSortingRepository<Pet, String>,
        JpaSpecificationExecutor<Pet> {

   Pet findByName (String name);

}
