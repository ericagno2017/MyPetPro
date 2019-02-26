package ar.com.enrique.clinicsmanager.service;

import ar.com.enrique.clinicsmanager.common.PageRequest;
import ar.com.enrique.clinicsmanager.common.PageResponse;
import ar.com.enrique.clinicsmanager.common.SpecificationBuilder;
import ar.com.enrique.clinicsmanager.model.Clinic;
import ar.com.enrique.clinicsmanager.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public void saveClinic(Clinic clinic) {
        clinicRepository.save(clinic);
    }

    public Clinic findClinicByName(String name) {
        return clinicRepository.findByName(name);
    }

    public Clinic findClinicById(String id) {
        return clinicRepository.findById(id).get();
    }

    public List<Clinic> findAll(String name, String id) {
        if (name == null && id == null) {
            Iterator<Clinic> iClinic = clinicRepository.findAll().iterator();
            List listClinic = new ArrayList<>();
            while (iClinic.hasNext()) {
                listClinic.add(iClinic.next());
            }
            return listClinic;
        } else {
            SpecificationBuilder<Clinic> spBuilder = new SpecificationBuilder<Clinic>()
                    .attrEqualsIfNotNull("name", name)
                    .attrEqualsIfNotNull("clinicId", id);
            return clinicRepository.findAll(spBuilder.getSpecification());
        }
    }

    public PageResponse<Clinic> findAllClinics(PageRequest pageRequest, String clinicName) {
        if (clinicName == null) {
            Iterator<Clinic> iClinic = clinicRepository.findAll().iterator();
            List listClinic = new ArrayList<>();
            while (iClinic.hasNext()) {
                listClinic.add(iClinic.next());
            }
            return PageResponse.fromList(listClinic, pageRequest.getPageNumber(), pageRequest.getPageSize(), Long.valueOf(listClinic.size()));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "clinicId");
            SpecificationBuilder<Clinic> spBuilder = new SpecificationBuilder<Clinic>()
                    .attrEqualsIfNotNull("name", clinicName).attrSimilarIfNotNull("name", clinicName);
            Page<Clinic> page = clinicRepository.findAll(spBuilder.getSpecification(), pageRequest.convert(sort));

            List<Clinic> views = page.getContent();
            return PageResponse.fromList(views, page.getNumber(), page.getSize(), page.getTotalElements());
        }
    }
}
