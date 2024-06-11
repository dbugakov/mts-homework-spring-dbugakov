package ru.siebel.spring.AnimalSpring.Model;

import java.time.LocalDate;

/**
 * Класс Shark, наследует {@link Predator}.
 *
 * @author Бугаков Данил
 * @version 1.0
 */
public class Shark extends Predator {
    public Shark(String name, Double cost, String character, LocalDate birthDate) {
        super(name, cost, character, birthDate);
    }

    public Shark() {
    }

    public Shark(String name) {
        this.name = name;
    }
}
