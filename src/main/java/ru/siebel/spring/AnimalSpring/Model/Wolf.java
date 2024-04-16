package ru.siebel.spring.AnimalSpring.Model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Класс Wolf, наследует {@link Predator}.
 *
 * @author Бугаков Данил
 * @version 1.0
 */
public class Wolf extends Predator {
    public Wolf(String name, Double cost, String character, LocalDate birthDate) {
        super(name, cost, character, birthDate);
    }

    public Wolf() {
    }
}
