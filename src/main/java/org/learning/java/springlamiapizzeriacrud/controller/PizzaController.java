package org.learning.java.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.learning.java.springlamiapizzeriacrud.model.Ingredienti;
import org.learning.java.springlamiapizzeriacrud.model.Pizza;
import org.learning.java.springlamiapizzeriacrud.repository.IngredientiRepository;
import org.learning.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IngredientiRepository ingredientiRepository;

    @GetMapping
    public String index(@RequestParam(name = "keyword") Optional<String> searchKeyword, Model model) {
        List<Pizza> pizze;
        String keyword = "";
        if (searchKeyword.isPresent()) {
            keyword = searchKeyword.get();
            pizze = pizzaRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        } else {
            pizze = pizzaRepository.findAll();
        }
        model.addAttribute("pizze", pizze);
        model.addAttribute("keyword", keyword);
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
        List<Ingredienti> ingredientiList = ingredientiRepository.findAll();
        model.addAttribute("ingredientiList", ingredientiList);
        model.addAttribute("pizza", new Pizza());
        return "/nuovapizzaform";
    }

    //Controller per aggiungere una nuova pizza alla nostra lista con usando POST
    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        //Controlliamo la possibile esistenza di errori
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientiList", ingredientiRepository.findAll());
            return "/nuovapizzaform";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    //Controller per UPDATE
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            model.addAttribute("ingredientiList", ingredientiRepository.findAll());
            return "/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + " not found");
        }
    }

    //PostMapping che riceve l'update di edit
    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientiList", ingredientiRepository.findAll());
            return "/edit";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    //Controller per DELETE
    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
        return "redirect:/pizze";
    }
}
