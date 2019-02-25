package ar.com.enrique.clinicsmanager.service;

import ar.com.enrique.clinicsmanager.common.PageRequest;
import ar.com.enrique.clinicsmanager.common.PageResponse;
import ar.com.enrique.clinicsmanager.common.SpecificationBuilder;
import ar.com.enrique.clinicsmanager.model.PetOwner;
import ar.com.enrique.clinicsmanager.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public void savePetOwner(PetOwner petOwner) {
        petOwnerRepository.save(petOwner);
    }

    public PetOwner findClinicByName(String name) {
        return petOwnerRepository.findByName(name);
    }

    public PetOwner findClinicById(String id) {
        return petOwnerRepository.findById(id).get();
    }

    public List<PetOwner> findAll(String name, String id) {
        if (name == null && id == null) {
            Iterator<PetOwner> petOwnerIterator = petOwnerRepository.findAll().iterator();
            List listPetOwner = new ArrayList<>();
            while (petOwnerIterator.hasNext()) {
                listPetOwner.add(petOwnerIterator.next());
            }
            return listPetOwner;
        } else {
            SpecificationBuilder<PetOwner> spBuilder = new SpecificationBuilder<PetOwner>()
                    .attrEqualsIfNotNull("name", name)
                    .attrEqualsIfNotNull("petOwnerId", id);
            return petOwnerRepository.findAll(spBuilder.getSpecification());
        }
    }

    public PageResponse<PetOwner> findAllPetOwners(PageRequest pageRequest, String petOwnerName) {
        if (petOwnerName == null) {
            Iterator<PetOwner> petOwnerIterator = petOwnerRepository.findAll().iterator();
            List listPetOwner = new ArrayList<>();
            while (petOwnerIterator.hasNext()) {
                listPetOwner.add(petOwnerIterator.next());
            }
            return PageResponse.fromList(listPetOwner
                    , pageRequest.getPageNumber()
                    , pageRequest.getPageSize()
                    , Long.valueOf(listPetOwner.size()));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "petOwnerId");
            SpecificationBuilder<PetOwner> spBuilder = new SpecificationBuilder<PetOwner>()
                    .attrEqualsIfNotNull("name", petOwnerName).attrSimilarIfNotNull("name", petOwnerName);
            Page<PetOwner> page = petOwnerRepository.findAll(spBuilder.getSpecification(), pageRequest.convert(sort));

            List<PetOwner> views = page.getContent();
            return PageResponse.fromList(views, page.getNumber(), page.getSize(), page.getTotalElements());
        }
    }
}
