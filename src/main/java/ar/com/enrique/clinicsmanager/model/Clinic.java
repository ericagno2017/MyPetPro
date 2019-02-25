package ar.com.enrique.clinicsmanager.model;

import ar.com.enrique.clinicsmanager.common.BackEndEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "clinic")
public class Clinic extends BackEndEntity {

    @JsonProperty("CLINIC_ID")
    @Id
    @Column(name = "CLINIC_ID", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;

    private String name;
    private String address;
    private String city;
    private String country;

    @OneToMany(mappedBy="clinic")
    @JsonProperty("usuarios")
    private Set<PetOwner> users = new HashSet<>();

    public Clinic() {
    }

    public Clinic(String name, String address, String city, String country, Set<PetOwner> users) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.users = users;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
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

    public Set<PetOwner> getUsers() {
        return users;
    }

    public void setUsers(Set<PetOwner> users) {
        this.users = users;
    }

    public void addPetOwner (PetOwner owner){
        users.add(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clinic)) return false;
        if (!super.equals(o)) return false;
        Clinic clinic = (Clinic) o;
        return Objects.equals(clinicId, clinic.clinicId) &&
                Objects.equals(name, clinic.name) &&
                Objects.equals(address, clinic.address) &&
                Objects.equals(city, clinic.city) &&
                Objects.equals(country, clinic.country) &&
                Objects.equals(users, clinic.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinicId, name, address, city, country, users);
    }
}
