package ar.com.enrique.clinicsmanager.service;

import ar.com.enrique.clinicsmanager.common.PageRequest;
import ar.com.enrique.clinicsmanager.common.PageResponse;
import ar.com.enrique.clinicsmanager.common.SpecificationBuilder;

import ar.com.enrique.clinicsmanager.model.Pet;
import ar.com.enrique.clinicsmanager.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public void savePet(Pet pet) {
        petRepository.save(pet);
    }

    public Pet findClinicByName(String name) {
        return petRepository.findByName(name);
    }

    public Pet findClinicById(String id) {
        return petRepository.findById(id).get();
    }

    public List<Pet> findAll(String name, String id) {
        if (name == null && id == null) {
            Iterator<Pet> petIterator = petRepository.findAll().iterator();
            List listPet = new ArrayList<>();
            while (petIterator.hasNext()) {
                listPet.add(petIterator.next());
            }
            return listPet;
        } else {
            SpecificationBuilder<Pet> spBuilder = new SpecificationBuilder<Pet>()
                    .attrEqualsIfNotNull("name", name)
                    .attrEqualsIfNotNull("petId", id);
            return petRepository.findAll(spBuilder.getSpecification());
        }
    }

    public PageResponse<Pet> findAllPets(PageRequest pageRequest, String clinicName) {
        if (clinicName == null) {
            Iterator<Pet> petIterator = petRepository.findAll().iterator();
            List listPet = new ArrayList<>();
            while (petIterator.hasNext()) {
                listPet.add(petIterator.next());
            }
            return PageResponse.fromList(listPet
                    , pageRequest.getPageNumber()
                    , pageRequest.getPageSize()
                    , Long.valueOf(listPet.size()));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "petId");
            SpecificationBuilder<Pet> spBuilder = new SpecificationBuilder<Pet>()
                    .attrEqualsIfNotNull("name", clinicName).attrSimilarIfNotNull("name", clinicName);
            Page<Pet> page = petRepository.findAll(spBuilder.getSpecification(), pageRequest.convert(sort));

            List<Pet> views = page.getContent();
            return PageResponse.fromList(views, page.getNumber(), page.getSize(), page.getTotalElements());
        }
    }
}
