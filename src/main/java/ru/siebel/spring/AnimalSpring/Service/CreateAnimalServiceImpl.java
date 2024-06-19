package ru.siebel.spring.AnimalSpring.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.siebel.spring.AnimalSpring.Api.Repository.AnimalRepository;
import ru.siebel.spring.AnimalSpring.Api.Service.CreateAnimalService;
import ru.siebel.spring.AnimalSpring.Model.Animal;

/**
 * Класс CreateAnimalServiceImpl, реализует {@link CreateAnimalService}.
 */
@Service
@Data
@Transactional
@RequiredArgsConstructor
public class CreateAnimalServiceImpl implements CreateAnimalService {

    @Autowired
    private final AnimalRepository animalRepository;

    @Autowired
    private final SearchServiceImpl searchService;

    @Override
    public void deleteAnimal(Long animalId) {
        if (animalRepository.existsById(animalId)) {
            animalRepository.deleteById(animalId);
        }
    }

    @Override
    public Long addAnimal(Animal animal) {
        animalRepository.save(animal);
        return animal.getId();
    }
}
