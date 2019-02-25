package ar.com.enrique.clinicsmanager.repository;

import ar.com.enrique.clinicsmanager.model.Clinic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends PagingAndSortingRepository<Clinic, String>,
        JpaSpecificationExecutor<Clinic>{

    Clinic findByName(String name);

}
