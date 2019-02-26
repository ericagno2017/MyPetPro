package ar.com.enrique.clinicsmanager;


import ar.com.enrique.clinicsmanager.model.Clinic;
import ar.com.enrique.clinicsmanager.model.Pet;
import ar.com.enrique.clinicsmanager.model.PetOwner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class PetIntegrationTest {

    private static final String BASE_URI = "http://localhost:8080/pet-pro";
    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    @Autowired
    private RestTemplate template;

    @Test
    public void test_get_all_pet() {
        ResponseEntity<Pet[]> response = template.getForEntity(BASE_URI + "/api/pets", Pet[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().length, greaterThan(0));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_pet_by_name(){
        test_create_new_pet();
        ResponseEntity<Pet[]> response = template.getForEntity("http://localhost:8080/pet-pro/api/pets?name=testPet&petOwnerId=1", Pet[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().length,greaterThan(0));
        validateCORSHttpHeaders(response.getHeaders());
    }


    @Test
    public void test_get_all_pet_paged() {
        ResponseEntity<Pet[]> response = template.getForEntity(BASE_URI + "/api/pets/paged?page-number=0&page-size=30", Pet[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().length, greaterThan(0));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_pet_by_id() {
        ResponseEntity<Pet> response = template.getForEntity(BASE_URI + "/api/pets/1", Pet.class);
        Pet petOwner = response.getBody();
        assertThat(petOwner.getPetId().toString(), is("1"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_create_new_pet(){
        ResponseEntity<PetOwner> response = template.getForEntity(BASE_URI+"/api/petowners/1", PetOwner.class);
        PetOwner petOwner = response.getBody();
        Pet newPet = new Pet("testPet", 5, "CABA", 15, petOwner);
        URI location = template.postForLocation(BASE_URI+"/api/savepet?petOwnerId=1", newPet, Pet.class);
        assertThat(location, notNullValue());
    }

    // =========================================== CORS Headers ===========================================

    public void validateCORSHttpHeaders(HttpHeaders headers) {
        assertThat(headers.getAccessControlAllowOrigin(), is("*"));
        assertThat(headers.getAccessControlAllowHeaders(), hasItem("*"));
        assertThat(headers.getAccessControlMaxAge(), is(3600L));
        assertThat(headers.getAccessControlAllowMethods(), hasItems(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.OPTIONS,
                HttpMethod.DELETE));
    }
}
