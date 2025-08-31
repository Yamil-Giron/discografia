package org.iplacex.proyectos.discografia.discos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import java.util.Optional;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/disco", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (artistaRepo.existsById(disco.idArtista)) {
            Disco creado = discoRepo.save(disco);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Artista no existe");
    }

    @GetMapping(
        value = "/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(discoRepo.findAll());
    }

    @GetMapping(
        value = "/disco/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> discoOpt = discoRepo.findById(id);
        if (discoOpt.isPresent()) {
            return ResponseEntity.ok(discoOpt.get());
        } 
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disco no encontrado");
        }
    }
    @GetMapping(
        value = "/artista/{id}/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        return ResponseEntity.ok(discoRepo.findDiscosByIdArtista(id));
    }
}