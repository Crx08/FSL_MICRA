package com.example.demo.Controller;

import com.example.demo.Libro;
import com.example.demo.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/libri")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> ottieniTuttiILibri() {
        return libroRepository.findAll();
    }
}