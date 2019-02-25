package ar.com.enrique.clinicsmanager.rest;


import ar.com.enrique.clinicsmanager.common.PageRequest;
import ar.com.enrique.clinicsmanager.common.PageResponse;
import ar.com.enrique.clinicsmanager.model.Clinic;
import ar.com.enrique.clinicsmanager.service.ClinicService;
import ar.com.enrique.clinicsmanager.utils.AppConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/api")
@Produces(MediaType.APPLICATION_JSON)

public class ClinicRestService {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/clinics")
    @ApiOperation(value = "Devuelve la lista de clinicas", notes = "Lista de Clinicas", response = Clinic.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> findAll(@Context HttpServletRequest request,
                                     @ApiParam(value = "nombre de la clinica") @RequestParam(value = "name", required = false) String name,
                                     @ApiParam(value = "id de la Clinica buscada") @RequestParam(value = "id", required = false) String id) {
        List<Clinic> result = clinicService.findAll(name, id);
        return new ResponseEntity<>(result, getHeadersOk(), HttpStatus.OK);
    }

    @GetMapping("/clinics/{clinicId}")
    @ApiOperation(value = "Devuelve una clinica", notes = "Clinica", response = Clinic.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> findOne(@Context HttpServletRequest request,
                                     @ApiParam(value = "id de la Clinica buscada") @PathVariable("clinicId") String id) {
        List<Clinic> result = clinicService.findAll(null, id);
        if (result.size() > 0) return new ResponseEntity<Clinic>(result.get(0), getHeadersOk(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/clinics/paged")
    @ApiOperation(value = "Devuelve la lista de clinicas", notes = "Busqueda paginada por nombre", response = Clinic.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<PageResponse<Clinic>> getAll(@Context HttpServletRequest request,
                                                       @ApiParam(value = "nombre de la clinica") @RequestParam(value = "name", required = false) String name,
                                                       @ApiParam(value = "nro de pagina a devolver") @RequestParam(value = "page-number", required = false, defaultValue = "0") String pageNumber,
                                                       @ApiParam(value = "cantidad de resultados a devolver") @RequestParam(value = "page-size", required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE ) String pageSize) {

        PageResponse<Clinic> page = clinicService.findAllClinics(new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize)), name);
        return new ResponseEntity<>(page, getHeadersOk(), HttpStatus.OK);
        //  return ResponseEntity.status(HttpStatus.ACCEPTED).body(page);
//                ok(page);
    }

    @PostMapping("/saveclinic")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Save Clinic", notes = "guarda una clinica nueva")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> saveClinic(@Context HttpServletRequest request,
                                        @ApiParam(value = "Json representativo de una clinica") @RequestBody Clinic clinic) {
        clinicService.saveClinic(clinic);
        return new ResponseEntity<>("", getHeadersOk(), HttpStatus.ACCEPTED);
    }

    private HttpHeaders getHeadersOk() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("status", "ok");
        responseHeaders.set("message", "succesfully");
        return responseHeaders;
    }
}