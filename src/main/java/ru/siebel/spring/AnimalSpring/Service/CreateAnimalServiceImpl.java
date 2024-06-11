package ru.siebel.spring.AnimalSpring.Service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.siebel.spring.AnimalSpring.Api.Model.Animal;
import ru.siebel.spring.AnimalSpring.Api.Service.CreateAnimalService;
import ru.siebel.spring.AnimalSpring.Model.AbstractAnimal;
import ru.siebel.spring.AnimalSpring.Util.FileUtil;

import java.util.*;

/**
 * Класс CreateAnimalServiceImpl, реализует {@link CreateAnimalService}.
 */
@Service
@Data
@Scope("prototype")
public class CreateAnimalServiceImpl implements CreateAnimalService {
    Map<String, List<Animal>> resultMap;
    Animal animal;
    String stringForFileFill = "";
    @Value("#{'${animal.cat.names}'.split(',')}")
    List<String> listOfNames;

    /**
     * Функция создания десяти уникальных животных.
     * Переопределён из {@link CreateAnimalService}.
     *
     * @return HashMap, где ключ - название класса, значение - массив Animal
     */
    @Override
    public Map<String, List<Animal>> createAnimals() {
        System.out.println("createAnimals - do while");
        resultMap = new HashMap<>();
        int i = 0;
        do {
            fillResultMap();
            fillFile(i + 1);
            i++;
        } while (i < 10);
        System.out.println(resultMap);
        return resultMap;
    }

    /**
     * Функция создания уникальных животных в заданном количестве.
     * Перегружен из метода {@link CreateAnimalServiceImpl#createAnimals}.
     *
     * @return HashMap, где ключ - название класса, значение - массив Animal
     */
    public Map<String, List<Animal>> createAnimals(int animalCount) {
        System.out.println("createAnimals - for");
        resultMap = new HashMap<>();
        for (int i = 0; i < animalCount; i++) {
            fillResultMap();
            fillFile(i + 1);
        }

        System.out.println(resultMap);
        return resultMap;
    }

    private void fillResultMap() {
        animal = AbstractAnimal.getRandomAnimal();
        Random random = new Random();
        animal.setName(listOfNames.get(random.nextInt(listOfNames.size())));
        if (resultMap.containsKey(animal.getClass().getSimpleName())) {
            resultMap.get(animal.getClass().getSimpleName()).add(animal);
        } else {
            resultMap.put(animal.getClass().getSimpleName(), new ArrayList<>());
            resultMap.get(animal.getClass().getSimpleName()).add(animal);
        }
        System.out.println(animal);
    }

    private void fillFile(int cnt) {
        stringForFileFill += cnt + " " + animal.getBreed() + " " + animal.getName() + " " + animal.getCost() + " " + animal.getBirthDate() + "\n";
        FileUtil.fileChannelWrite(stringForFileFill, "src/main/resources/animals/logData.txt");
    }
}
