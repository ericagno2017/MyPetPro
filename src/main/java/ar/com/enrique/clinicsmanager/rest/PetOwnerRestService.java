package ar.com.enrique.clinicsmanager.rest;

import ar.com.enrique.clinicsmanager.common.PageRequest;
import ar.com.enrique.clinicsmanager.common.PageResponse;
import ar.com.enrique.clinicsmanager.model.Clinic;
import ar.com.enrique.clinicsmanager.model.Pet;
import ar.com.enrique.clinicsmanager.model.PetOwner;
import ar.com.enrique.clinicsmanager.service.ClinicService;
import ar.com.enrique.clinicsmanager.service.PetOwnerService;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PetOwnerRestService {

    @Autowired
    private PetOwnerService petOwnerService;

    @GetMapping("/petowners")
    @ApiOperation(value = "Devuelve la lista de dueños por clinica", notes = "Lista de dueños", response = PetOwner.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> findAll(@Context HttpServletRequest request,
                                     @ApiParam(value = "nombre del  Dueño") @RequestParam(value = "name", required = false) String name,
                                   //  @ApiParam(value = "id del dueño buscado") @RequestParam(value = "id", required = false) String id,
                                     @ApiParam(value = "Id de Clinica") @RequestParam(value = "clinicId", required = false) String clinicId) {
        List<PetOwner> result = petOwnerService.findAll(name, null);
        List<PetOwner> returnResult = getPetOwners(clinicId, result);

        return new ResponseEntity<>(returnResult, getHeadersOk(), HttpStatus.OK);
    }

    @GetMapping("/petOwners/{petOwnerId}")
    @ApiOperation(value = "Devuelve un dueño", notes = "Dueño", response = PetOwner.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> findOne(@Context HttpServletRequest request,
                                     @ApiParam(value = "id del dueño buscado") @PathVariable("petOwnerId") String id) {
        List<PetOwner> result = petOwnerService.findAll(null, id);
        if (result.size() > 0) return new ResponseEntity<PetOwner>(result.get(0), getHeadersOk(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/petowners/paged")
    @ApiOperation(value = "Devuelve la lista de Dueños", notes = "Busqueda paginada por nombre", response = PetOwner.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<List<PetOwner>> getAll(@Context HttpServletRequest request,
                                                         @ApiParam(value = "nombre del dueño") @RequestParam(value = "name", required = false) String name,
                                                         @ApiParam(value = "Id de Clinica") @RequestParam(value = "clinicId", required = false) String clinicId,
                                                         @ApiParam(value = "nro de pagina a devolver") @RequestParam(value = "page-number", required = false, defaultValue = "0") String pageNumber,
                                                         @ApiParam(value = "cantidad de resultados a devolver") @RequestParam(value = "page-size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) String pageSize) {


        List<PetOwner> result = petOwnerService.findAllPetOwners(new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize)), name).getItems();
        List<PetOwner> returnResult = getPetOwners(clinicId, result);
//        PageResponse<PetOwner> page = new PageResponse<PetOwner>();
//        page.setItems(returnResult);
        return new ResponseEntity<>(returnResult, getHeadersOk(), HttpStatus.OK);
    }

    private List<PetOwner> getPetOwners (String clinicId, List<PetOwner> result) {
        List<PetOwner> returnResult = new ArrayList<>();
        if (clinicId != null) {
            Iterator<PetOwner> petOwnerIterator = result.iterator();
            while (petOwnerIterator.hasNext()) {
                PetOwner petOwner = petOwnerIterator.next();
                if (petOwner.getClinic().getClinicId().toString().equals(clinicId)) {
                    returnResult.add(petOwner);
                }
            }
        } else {
            returnResult = result;
        }
        return returnResult;
    }

    @PostMapping("/savepetowner")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Save PetOwner", notes = "guarda un dueño nuevo")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> savePetOwner(@Context HttpServletRequest request,
                                        @ApiParam(value = "Json representativo de un dueño") @RequestBody PetOwner petOwner,
                                        @ApiParam(value = "id de la clinica") @RequestParam(value = "clinicId", required = true) String id) {
        if (petOwner.getClinic().getClinicId() == null) {
            if (id != null) petOwner.getClinic().setClinicId(Long.valueOf(id));
        }
        petOwnerService.savePetOwner(petOwner);
        return new ResponseEntity<>("", getHeadersOk(), HttpStatus.ACCEPTED);
    }

    private HttpHeaders getHeadersOk() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("status", "ok");
        responseHeaders.set("message", "succesfully");
        return responseHeaders;
    }
}
