package ar.com.enrique.clinicsmanager.model;

import ar.com.enrique.clinicsmanager.common.BackEndEntity;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "user")
public class PetOwner extends BackEndEntity {

    @JsonProperty("USER_ID")
    @Id
    @Column(name = "USER_ID", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petOwnerId;

    private String name;
    private String address;
    private String city;
    private String country;

    @OneToMany(mappedBy = "user")
    @JsonProperty("mascotas")
    private Set<Pet> pets = new HashSet<>();

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id"
             ,nullable = false)
    @JsonIgnore
    private Clinic clinic;

    public PetOwner() {
    }

    public PetOwner(String name, String address, String city, String country, Clinic clinic) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.clinic = clinic;
    }

    public Long getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(Long petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public void addPet (Pet pet){
        pets.add(pet);
    }



    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetOwner)) return false;
        if (!super.equals(o)) return false;
        PetOwner petOwner = (PetOwner) o;
        return Objects.equals(petOwnerId, petOwner.petOwnerId) &&
                Objects.equals(name, petOwner.name) &&
                Objects.equals(address, petOwner.address) &&
                Objects.equals(city, petOwner.city) &&
                Objects.equals(country, petOwner.country) &&
                Objects.equals(pets, petOwner.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petOwnerId, name, address, city, country, pets);
    }
}
