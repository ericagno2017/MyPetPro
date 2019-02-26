package ar.com.enrique.clinicsmanager;

import ar.com.enrique.clinicsmanager.model.Clinic;

import ar.com.enrique.clinicsmanager.model.PetOwner;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class ClinicIntegrationTest{

    private static final String BASE_URI = "http://localhost:8080/pet-pro";
    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    @Autowired
    private RestTemplate template;

    @Test
    public void test_get_all_clinic(){
        ResponseEntity<Clinic[]> response = template.getForEntity(BASE_URI+"/api/clinics", Clinic[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().length,greaterThan(0));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_clinic_by_name(){
        ResponseEntity<Clinic[]> response = template.getForEntity(BASE_URI+"/api/clinics?name=PetOne", Clinic[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().length,greaterThan(0));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_all_clinic_paged(){
        ResponseEntity<Clinic[]> response = template.getForEntity(BASE_URI+"/api/clinics/paged?page-number=0&page-size=30", Clinic[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().length,greaterThan(0));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_clinic_by_id(){
        ResponseEntity<Clinic> response = template.getForEntity(BASE_URI+"/api/clinics/1", Clinic.class);
        Clinic clinic = response.getBody();
        assertThat(clinic.getClinicId().toString(), is("1"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_create_new_clinic(){
        Clinic newClinic = new Clinic("test", "nueva direccion", "CABA", "ARG", new HashSet<>());
        URI location = template.postForLocation(BASE_URI+"/api/saveclinic", newClinic, Clinic.class);
        assertThat(location, notNullValue());
    }

    // =========================================== CORS Headers ===========================================

    public void validateCORSHttpHeaders(HttpHeaders headers){
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
