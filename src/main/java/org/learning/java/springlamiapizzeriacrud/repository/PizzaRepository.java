package org.learning.java.springlamiapizzeriacrud.repository;


import org.learning.java.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    List<Pizza> findByNameContainingOrDescriptionContaining(String nameSearch, String descriptionSearch);
}
