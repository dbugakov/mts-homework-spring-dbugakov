package ru.siebel.spring.AnimalSpring.Api.Service;


import ru.siebel.spring.AnimalSpring.Model.Animal;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;

/**
 * CreateAnimalService, реализован в {@link CreateAnimalServiceImpl}.
 */
public interface CreateAnimalService {
    void deleteAnimal(Long animalId);

    Long addAnimal(Animal animal);
}
