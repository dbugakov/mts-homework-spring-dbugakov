package ru.siebel.spring.AnimalSpring.Model;

import java.time.LocalDate;

/**
 * Класс Pet, наследует {@link AbstractAnimal}.
 *
 * @author Бугаков Данил
 * @version 1.0
 */
public class Pet extends AbstractAnimal {
    public Pet(String name, Double cost, String character, LocalDate birthDate) {
        super(name, cost, character, birthDate);
    }

    public Pet() {

    }
}
