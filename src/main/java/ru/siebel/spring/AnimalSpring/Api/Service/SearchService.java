package ru.siebel.spring.AnimalSpring.Api.Service;

import ru.siebel.spring.AnimalSpring.Api.Model.Animal;
import ru.siebel.spring.AnimalSpring.Exception.InvalidAnimalBirthDateException;
import ru.siebel.spring.AnimalSpring.Service.SearchServiceImpl;

/**
 * SearchService, реализован в {@link SearchServiceImpl}.
 */

public interface SearchService {
    boolean checkLeapYearAnimal(Animal animal) throws InvalidAnimalBirthDateException, InvalidAnimalBirthDateException;
}
