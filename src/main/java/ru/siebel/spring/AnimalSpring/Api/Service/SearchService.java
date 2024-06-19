package ru.siebel.spring.AnimalSpring.Api.Service;

import ru.siebel.spring.AnimalSpring.Exception.InvalidAnimalBirthDateException;
import ru.siebel.spring.AnimalSpring.Model.Animal;
import ru.siebel.spring.AnimalSpring.Service.SearchServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * SearchService, реализован в {@link SearchServiceImpl}.
 */

public interface SearchService {
    boolean checkLeapYearAnimalById(Long animalId) throws InvalidAnimalBirthDateException, InvalidAnimalBirthDateException;

    Map<String, LocalDate> findLeapYearNames();

    Map<Animal, Integer> findOlderAnimal(int age) throws IOException;

    Map<String, List<Animal>> findDuplicate();

    List<String> findMinCostAnimals();

    double findAverageAge();

    List<String> findOldAndExpensive();
}
