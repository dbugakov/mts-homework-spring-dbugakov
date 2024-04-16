package ru.siebel.spring.AnimalSpring.Exception;

/**
 * InvalidAnimalBirthDateException, наследует {@link Exception}.
 */
public class InvalidAnimalBirthDateException extends Exception {
    public InvalidAnimalBirthDateException(String message) {
        super(message);
    }
}
