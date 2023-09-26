package org.learning.java.springlamiapizzeriacrud.controller;

import org.learning.java.springlamiapizzeriacrud.model.Offerte;
import org.learning.java.springlamiapizzeriacrud.model.Pizza;
import org.learning.java.springlamiapizzeriacrud.repository.OfferteRepository;
import org.learning.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offerte")
public class OfferteController {
    @Autowired
    private OfferteRepository offerteRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    //Metodo per aggiungere un'offerta
    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        Optional<Pizza> risultatoPizza = pizzaRepository.findById(pizzaId);

        if (risultatoPizza.isPresent()) {
            Pizza pizza = risultatoPizza.get();
            Offerte offerta = new Offerte();
            offerta.setPizza(pizza);
            offerta.setDataInizio(LocalDate.now());
            offerta.setDataFine(LocalDate.now().plusDays(15));
            model.addAttribute("offerta", offerta);
            return "offerte/form";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + pizzaId + " non trovata");
        }
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("offerta") Offerte offertaForm) {
        offerteRepository.save(offertaForm);
        return "redirect:/pizze/detail/" + offertaForm.getPizza().getId();
    }


    //Metodo per modificare un' offerta
    @GetMapping("/edit/{offertaId}")
    public String edit(@RequestParam("offertaId") Integer id, Model model) {
        Optional<Offerte> risultatoOfferta = offerteRepository.findById(id);
        if (risultatoOfferta.isPresent()) {
            model.addAttribute("offerta", risultatoOfferta.get());
            return "/offerte/edit";

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{offertaId}")
    public String doEdit(@PathVariable("offertaId") Integer offertaId, @ModelAttribute("offerta") Offerte offertaForm) {
        offertaForm.setId(offertaId);
        offerteRepository.save(offertaForm);
        return "redirect:/pizze/detail/" + offertaForm.getPizza().getId();
    }

    //Metodo per la delete
    @PostMapping("/delete/{offertaId}")
    public String delete(@PathVariable("offertaId") Integer id) {
        Optional<Offerte> risultatoOfferta = offerteRepository.findById(id);
        if (risultatoOfferta.isPresent()) {
            Integer pizzaId = risultatoOfferta.get().getPizza().getId();
            offerteRepository.deleteById(id);
            return "redirect:/pizze/detail/" + pizzaId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
