package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // <-- Aggiunta
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // <-- Aggiunta
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hello")
    public String sayHello() {
        return "Ciao dal tuo primo progetto Spring Boot!";
    }

    @GetMapping("/saluta/{nome}")
    public String salutaPersona(@PathVariable String nome) {
        return "Ciao, " + nome + "!";
    }


    @PostMapping("/invia")
    public String riceviDati(@RequestBody String datiRicevuti) {
        return "Ho ricevuto i tuoi dati: " + datiRicevuti;
    }
}