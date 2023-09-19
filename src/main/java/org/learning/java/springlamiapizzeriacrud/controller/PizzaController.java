package org.learning.java.springlamiapizzeriacrud.controller;


import org.learning.java.springlamiapizzeriacrud.model.Pizza;
import org.learning.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzaRepository.findAll();
        model.addAttribute("pizze", pizzaList);

        return "home";
    }

    //Aggiungiamo il metodo per vedere i dettagli della pizza
    @GetMapping("/show/{pizzaid}")
    public String show(@PathVariable("pizzaid") Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);

        if (pizzaOptional.isPresent()) {
            Pizza pizzaFromDb = pizzaOptional.get();
            model.addAttribute("pizza", pizzaFromDb);
            return "/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    //Controller che mostra la pagina per la creazione di una nuova pizza
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/nuovapizzaform";
    }

}
