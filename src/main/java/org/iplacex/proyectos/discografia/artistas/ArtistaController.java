package org.iplacex.proyectos.discografia.artistas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistarepo;

    @PostMapping(
        value = "/artista", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista creado = artistarepo.save(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping(
        value = "/artistas", 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        return ResponseEntity.ok(artistarepo.findAll());
    }

    @GetMapping(
        value = "/artista/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> temp = artistarepo.findById(id);
        
        if (!temp.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
    }

    @PutMapping(
        value = "/artista/{id}", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (artistarepo.existsById(id)) {
            artista._id = id;
            Artista actualizado = artistarepo.save(artista);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
    }

    @DeleteMapping(
        value = "/artista/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (artistarepo.existsById(id)) {
            artistarepo.deleteById(id);
            return ResponseEntity.ok("Artista eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado");
    }
}
