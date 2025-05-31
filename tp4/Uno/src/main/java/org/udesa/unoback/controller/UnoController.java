package org.udesa.unoback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnoController {

    @GetMapping("/")
    public String saludo() {
        return "Estos son los alumnos de Emilio.";
    }

}
