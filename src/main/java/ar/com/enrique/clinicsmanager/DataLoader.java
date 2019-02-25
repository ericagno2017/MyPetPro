package ar.com.enrique.clinicsmanager;

import ar.com.enrique.clinicsmanager.model.Clinic;
import ar.com.enrique.clinicsmanager.model.Pet;
import ar.com.enrique.clinicsmanager.model.PetOwner;
import ar.com.enrique.clinicsmanager.repository.ClinicRepository;
import ar.com.enrique.clinicsmanager.repository.PetOwnerRepository;
import ar.com.enrique.clinicsmanager.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader  implements ApplicationRunner {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Clinic clinica = new Clinic();
        clinica.setName("PetOne");
        clinica.setAddress("Mercedes 1000");
        clinica.setCity("CABA");
        clinica.setCountry("ARGENTINA");


        clinicRepository.save(clinica);

        crearUsuariosClinica(clinica);


        Clinic clinica1 = new Clinic();
        clinica1.setName("PetTwo");
        clinica1.setAddress("Mercedes 1000");
        clinica1.setCity("CABA");
        clinica1.setCountry("ARGENTINA");

        clinicRepository.save(clinica1);

        crearUsuariosClinica(clinica1);




    }

    private  void crearUsuariosClinica (Clinic clinic){
        for (int i = 0; i < 2;i++){
            PetOwner duenho = new PetOwner();
            duenho.setAddress("Mercedes 1001");
            duenho.setCity("CABA");
            duenho.setName("dueño " + i + " " + clinic.getClinicId());
            duenho.setCountry("ARGENTINA");
            duenho.setClinic(clinic);
            petOwnerRepository.save(duenho);
            for (int m = 0; m < 5; m ++){
                Pet pet = new Pet();
                pet.setAge(5 + m);
                pet.setName("Pedro " + m + " de " + duenho.getPetOwnerId());
                pet.setRace("Perro");
                pet.setSize(2 * m);
                pet.setUser(duenho);
                duenho.getPets().add(pet);
                petRepository.save(pet);
            }
        }

    }
}
