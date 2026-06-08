package com.example.demo.controller;

import com.example.demo.entity.Autore;
import com.example.demo.service.AutoreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/autori")
public class AutoreController {

    private final AutoreService service;

    public AutoreController(AutoreService service) {
        this.service = service;
    }

    // GET http://localhost:8080/autori
    @GetMapping
    public List<Autore> getAll() {
        return service.findAll();
    }

    // GET http://localhost:8080/autori/1
    @GetMapping("/{id}")
    public Autore getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST http://localhost:8080/autori
    @PostMapping
    public Autore create(@RequestBody Autore autore) {
        return service.save(autore);
    }

    // PUT http://localhost:8080/autori/1
    @PutMapping("/{id}")
    public Autore update(@PathVariable Long id, @RequestBody Autore autore) {
        return service.update(id, autore);
    }

    // DELETE http://localhost:8080/autori/1
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Autore con id " + id + " eliminato.";
    }
}
