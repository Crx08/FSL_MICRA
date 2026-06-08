package com.example.demo.controller;

import com.example.demo.entity.Prestito;
import com.example.demo.service.PrestitoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/prestiti")
public class PrestitoController {

    private final PrestitoService service;

    public PrestitoController(PrestitoService service) {
        this.service = service;
    }

    // GET http://localhost:8080/prestiti
    @GetMapping
    public List<Prestito> getAll() {
        return service.findAll();
    }

    // GET http://localhost:8080/prestiti/1
    @GetMapping("/{id}")
    public Prestito getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // GET http://localhost:8080/prestiti/attiva?idUtente=1&idCopia=1
    @GetMapping("/attiva")
    public String attiva(@RequestParam Long idUtente, @RequestParam Long idCopia) {
        return service.attiva(idUtente, idCopia);
    }

    // GET http://localhost:8080/prestiti/restituisci?idPrestito=1
    @GetMapping("/restituisci")
    public String restituisci(@RequestParam Long idPrestito) {
        return service.restituisci(idPrestito);
    }

    // DELETE http://localhost:8080/prestiti/1
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Prestito con id " + id + " eliminato.";
    }
}
