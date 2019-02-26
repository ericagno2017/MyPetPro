package ar.com.enrique.clinicsmanager;

import ar.com.enrique.clinicsmanager.rest.ClinicRestService;
import ar.com.enrique.clinicsmanager.service.ClinicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClinicsmanagerApplicationTests {

    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    private MockMvc mockMvc;

    @Mock
    private ClinicService clinicService;

    @InjectMocks
    private ClinicRestService clinicRestService;


    @Test
    public void contextLoads() {
    }

}
