package ru.siebel.spring.AnimalSpring.Model;

import java.time.LocalDate;

/**
 * Класс Cat, наследует {@link Pet}.
 *
 * @author Бугаков Данил
 * @version 1.0
 */
public class Cat extends Pet {
    public Cat(String name, Double cost, String character, LocalDate birthDate) {
        super(name, cost, character, birthDate);
    }

    public Cat() {

    }

    public Cat(String name) {
        this.name = name;
    }
}
