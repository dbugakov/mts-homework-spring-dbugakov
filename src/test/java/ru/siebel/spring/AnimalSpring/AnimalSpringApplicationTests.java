package ru.siebel.spring.AnimalSpring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.siebel.spring.AnimalSpring.Model.Animal;
import ru.siebel.spring.AnimalSpring.Model.AnimalType;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;
import ru.siebel.spring.AnimalSpring.Service.SearchServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
@TestPropertySource("/test.properties")
class AnimalSpringApplicationTests {
    @Autowired
    private CreateAnimalServiceImpl createAnimalService;
    @Autowired
    private SearchServiceImpl searchService;

    Animal animal;

    Long AnimalId;

    @Test
    void createAnimalPositive() {
        System.out.println("addAnimal");
        animal = new Animal("Мурзик", 321.12, "Хочет поесть", LocalDate.of(2011, 1, 6));
        List<AnimalType> animalTypes = createAnimalService.getAnimalRepository().getAnimalTypeByName("Волк");
        if (!animalTypes.isEmpty()) {
            animal.setBreed(animalTypes.get(0));
        }
        AnimalId = createAnimalService.addAnimal(animal);
        assertTrue(searchService.getAnimalRepository().getAnimalById(AnimalId).isPresent());
    }

    @Test
    void deleteAnimalPositive() {
        if (AnimalId != null) {
            System.out.println("deleteAnimal");
            createAnimalService.deleteAnimal(AnimalId);
            assertFalse(createAnimalService.getAnimalRepository().existsById(AnimalId));
        }
    }

}
