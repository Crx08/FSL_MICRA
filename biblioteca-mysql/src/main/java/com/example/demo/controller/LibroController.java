package com.example.demo.controller;

import com.example.demo.entity.Libro;
import com.example.demo.service.LibroService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/libri")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    // GET http://localhost:8080/libri
    @GetMapping
    public List<Libro> getAll() {
        return service.findAll();
    }

    // GET http://localhost:8080/libri/9788845292613
    @GetMapping("/{isbn}")
    public Libro getById(@PathVariable String isbn) {
        return service.findById(isbn);
    }

    // POST http://localhost:8080/libri
    @PostMapping
    public Libro create(@RequestBody Libro libro) {
        return service.save(libro);
    }

    // PUT http://localhost:8080/libri/9788845292613
    @PutMapping("/{isbn}")
    public Libro update(@PathVariable String isbn, @RequestBody Libro libro) {
        return service.update(isbn, libro);
    }

    // DELETE http://localhost:8080/libri/9788845292613
    @DeleteMapping("/{isbn}")
    public String delete(@PathVariable String isbn) {
        service.delete(isbn);
        return "Libro con ISBN " + isbn + " eliminato.";
    }
}
