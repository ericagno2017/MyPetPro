package ar.com.enrique.clinicsmanager.rest;

import ar.com.enrique.clinicsmanager.common.PageRequest;
import ar.com.enrique.clinicsmanager.model.Pet;
import ar.com.enrique.clinicsmanager.service.PetService;
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
public class PetRestService {


    @Autowired
    private PetService petService;

    @GetMapping("/pets")
    @ApiOperation(value = "Devuelve la lista de mascotas por dueño", notes = "Lista de mascotas", response = Pet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> findAll(@Context HttpServletRequest request,
                                     @ApiParam(value = "nombre del la mascota") @RequestParam(value = "name", required = false) String name,
                                    // @ApiParam(value = "id de la mascota") @RequestParam(value = "id", required = false) String id,
                                     @ApiParam(value = "Id del dueño") @RequestParam(value = "petOwnerId", required = false) String petOwnerId) {
        List<Pet> result = petService.findAll(name, null);
        List<Pet> returnResult = getPets(petOwnerId, result);
        return new ResponseEntity<>(returnResult, getHeadersOk(), HttpStatus.OK);
    }

    @GetMapping("/pets/{petId}")
    @ApiOperation(value = "Devuelve una mascota", notes = "Mascota", response = Pet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> findOne(@Context HttpServletRequest request,
                                     @ApiParam(value = "id del la mascota buscada") @PathVariable("petId") String id) {
        List<Pet> result = petService.findAll(null, id);
        if (result.size() > 0) return new ResponseEntity<Pet>(result.get(0), getHeadersOk(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pets/paged")
    @ApiOperation(value = "Devuelve la lista de Mascotas", notes = "Busqueda paginada por nombre", response = Pet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<List<Pet>> getAll(@Context HttpServletRequest request,
                                                 @ApiParam(value = "nombre de la mascota") @RequestParam(value = "name", required = false) String name,
                                                 @ApiParam(value = "Id del Dueño") @RequestParam(value = "petOwnerId", required = false) String petOwnerId,
                                                 @ApiParam(value = "nro de pagina a devolver") @RequestParam(value = "page-number", required = false, defaultValue = "0") String pageNumber,
                                                 @ApiParam(value = "cantidad de resultados a devolver") @RequestParam(value = "page-size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) String pageSize) {


        List<Pet> result = petService.findAllPets(new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize)), name).getItems();
        List<Pet> returnResult = getPets(petOwnerId, result);
        return new ResponseEntity<>(returnResult, getHeadersOk(), HttpStatus.OK);
    }

    private List<Pet> getPets (String petOwnerId, List<Pet> result) {
        List<Pet> returnResult = new ArrayList<>();
        if (petOwnerId != null) {
            Iterator<Pet> petIterator = result.iterator();
            while (petIterator.hasNext()) {
                Pet pet = petIterator.next();
                if (pet.getUser().getPetOwnerId().toString().equals(petOwnerId)) {
                    returnResult.add(pet);
                }
            }
        } else {
            returnResult = result;
        }
        return returnResult;
    }

    @PostMapping("/savepet")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Save Pet", notes = "guarda un dueño nuevo")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    public ResponseEntity<?> savePet(@Context HttpServletRequest request,
                                        @ApiParam(value = "Json representativo de una mascota") @RequestBody Pet pet,
                                        @ApiParam(value = "id del dueño") @RequestParam(value = "petOwnerId", required = true) String id) {
        if (pet.getUser().getPetOwnerId() == null) {
            if (id != null) pet.getUser().setPetOwnerId(Long.valueOf(id));
        }
        petService.savePet(pet);
        return new ResponseEntity<>("", getHeadersOk(), HttpStatus.ACCEPTED);
    }

    private HttpHeaders getHeadersOk() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("status", "ok");
        responseHeaders.set("message", "succesfully");
        return responseHeaders;
    }

}
