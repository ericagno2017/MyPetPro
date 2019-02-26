package ar.com.enrique.clinicsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="ar.com.enrique.clinicsmanager.repository")
public class ClinicsmanagerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ClinicsmanagerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ClinicsmanagerApplication.class);
    }
}
