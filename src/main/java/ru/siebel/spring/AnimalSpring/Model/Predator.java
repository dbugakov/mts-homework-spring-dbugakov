package ru.siebel.spring.AnimalSpring.Model;

import java.time.LocalDate;

/**
 * Класс Predator, наследует {@link AbstractAnimal}.
 *
 * @author Бугаков Данил
 * @version 1.0
 */
public class Predator extends AbstractAnimal {
    public Predator(String name, Double cost, String character, LocalDate birthDate) {
        super(name, cost, character, birthDate);
    }

    public Predator() {
    }
}
