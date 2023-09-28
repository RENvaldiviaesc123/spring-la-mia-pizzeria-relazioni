package org.learning.java.springlamiapizzeriacrud.controller;


import org.learning.java.springlamiapizzeriacrud.model.Ingredienti;
import org.learning.java.springlamiapizzeriacrud.repository.IngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredienti")
public class IngredientiController {
    @Autowired
    private IngredientiRepository ingredientiRepository;

    //Metodo che mostra la lista degli ingredienti e anche il form per aggiungere un ingrediente
    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredientiList", ingredientiRepository.findAll());
        model.addAttribute("ingredienteObj", new Ingredienti());
        return "ingredients/addingredients";
    }

    //Metodo doCreate che salva l'ingrediente
    @PostMapping("/create")
    public String doCreate(@ModelAttribute("ingredienteObj") Ingredienti ingredienteForm, RedirectAttributes redirectAttributes) {
        ingredientiRepository.save(ingredienteForm);
        redirectAttributes.addFlashAttribute("message", "Ingrediente aggiunto con successo!");

        return "redirect:/ingredienti";
    }

    //Metodo che cancella un ingrediente
    @PostMapping("/delete/{ingId}")
    public String delete(@PathVariable("ingId") Integer id) {
        ingredientiRepository.deleteById(id);
        return "redirect:/ingredienti";
    }


}
