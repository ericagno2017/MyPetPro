package ar.com.enrique.clinicsmanager.model;

import ar.com.enrique.clinicsmanager.common.BackEndEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "MASCOTA")
public class Pet extends BackEndEntity {

    @JsonProperty("PET_ID")
    @Id
    @Column(name = "PET_ID", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String name;
    private Integer age;
    private String race;
    private Integer size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id"
            , nullable = false)
    @JsonIgnore
    private PetOwner user;

    public Pet() {
    }

    public Pet(String name, Integer age, String race, Integer size, PetOwner user) {
        this.name = name;
        this.age = age;
        this.race = race;
        this.size = size;
        this.user = user;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public PetOwner getUser() {
        return user;
    }

    public void setUser(PetOwner user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        if (!super.equals(o)) return false;
        Pet pet = (Pet) o;
        return Objects.equals(petId, pet.petId) &&
                Objects.equals(name, pet.name) &&
                Objects.equals(age, pet.age) &&
                Objects.equals(race, pet.race) &&
                Objects.equals(size, pet.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, name, age, race, size);
    }
}
