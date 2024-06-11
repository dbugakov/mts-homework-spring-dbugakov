package ru.siebel.spring.AnimalSpring.Exception;

/**
 * InvalidAnimalException, наследует в {@link RuntimeException}.
 */

public class InvalidAnimalException extends RuntimeException {

    public InvalidAnimalException(String message) {
        super(message);
    }
}
