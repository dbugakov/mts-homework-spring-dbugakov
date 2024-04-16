package ru.siebel.spring.AnimalSpring.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Repository;
import ru.siebel.spring.AnimalSpring.Api.Model.Animal;
import ru.siebel.spring.AnimalSpring.Api.Repository.AnimalRepository;
import ru.siebel.spring.AnimalSpring.Exception.InvalidAnimalBirthDateException;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;
import ru.siebel.spring.AnimalSpring.Service.SearchServiceImpl;
import ru.siebel.spring.AnimalSpring.Util.FileUtil;
import ru.siebel.spring.AnimalSpring.Util.ResultReader;
import ru.siebel.spring.AnimalSpring.Util.SerializeUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс AnimalRepositoryImpl, реализует {@link AnimalRepository}.
 */
@Repository
public class AnimalRepositoryImpl implements AnimalRepository {

    @Override
    @PostConstruct
    public Map<String, List<Animal>> createAnimals() {
        CreateAnimalServiceImpl animalService = new CreateAnimalServiceImpl();
        return animalService.createAnimals();
    }

    /**
     * Функция получения HashMap с животными, которые родились в високосный год
     * Переопределён из {@link AnimalRepository}
     *
     * @param animalList список животных
     * @return HashMap, где ключ - название класса + поле name, значение - поле дата рождения
     */
    @Override
    public Map<String, LocalDate> findLeapYearNames(List<Animal> animalList) {
        SearchServiceImpl searchService = new SearchServiceImpl();
        return animalList.stream()
                .filter(animal -> {
                    try {
                        return searchService.checkLeapYearAnimal(animal);
                    } catch (InvalidAnimalBirthDateException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toMap(animal -> animal.getClass().getSimpleName() + " " + animal.getName(), Animal::getBirthDate));
    }

    /**
     * Функция получения HashMap с животными, которые старше параметра age
     * Если ни одного животного с указанным годом нет, ищет самого старшего
     * Переопределён из {@link AnimalRepository}
     *
     * @param animalList список животных
     * @param age        целевой возраст
     * @return HashMap, где ключ - экземпляр животного, значение - возраст животного
     */
    @Override
    public Map<Animal, Integer> findOlderAnimal(List<Animal> animalList, int age) throws IOException {
        Map<Animal, Integer> resultMap = animalList.stream()
                .filter(animal -> Period.between(animal.getBirthDate(), LocalDate.now()).getYears() > age)
                .collect(Collectors.toMap(Function.identity(), animal -> (int) findAnimalAge(animal)));
        if (resultMap.isEmpty()) {
            resultMap = animalList.stream()
                    .sorted(Comparator.naturalOrder())
                    .limit(1)
                    .collect(Collectors.toMap(Function.identity(), animal -> (int) findAnimalAge(animal)));
        }
        FileUtil.fileChannelWrite(this.convertToJson(resultMap.keySet()), "Animals/src/main/resources/results/findOlderAnimals.json");
        ResultReader.printDeserializeAnimal();
        return resultMap;
    }

    /**
     * Функция получения HashMap с животными, у которых есть дубликаты
     * Переопределён из {@link AnimalRepository}
     *
     * @param animalList список животных
     * @return HashMap, где ключ - название класса, значение - список дубликатов
     */
    @Override
    public Map<String, List<Animal>> findDuplicate(List<Animal> animalList) {
        return animalList.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .values().stream()
                .filter(animals -> animals.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(animal -> animal.getClass().getSimpleName()));
    }

    /**
     * Функция получения среднего возраста из списка животных.
     * Ничего не возвращает печатает на экран.
     * Переопределён из {@link AnimalRepository}
     *
     * @param animalList список животных
     */
    @Override
    public double findAverageAge(List<Animal> animalList) {
        double result = animalList.stream()
                .mapToDouble(this::findAnimalAge)
                .average()
                .orElse(Double.NaN);
        System.out.println("Средний возраст животных равен = " + result);
        return result;
    }

    /**
     * Функция получения списка с животными, возраст которых >5 лет и стоимость выше средней.
     * Переопределён из {@link AnimalRepository}
     *
     * @param animalList список животных
     * @return List - список имён, отсортированный по дате рождения (по возрастанию)
     */
    @Override
    public List<String> findOldAndExpensive(List<Animal> animalList) {
        double averageCost = animalList.stream()
                .mapToDouble(Animal::getCost)
                .average()
                .orElse(Double.NaN);
        return animalList.stream()
                .filter(animal -> findAnimalAge(animal) > 5 && animal.getCost() > averageCost)
                .sorted(Comparator.naturalOrder())
                .map(Animal::getName)
                .collect(Collectors.toList());
    }

    /**
     * Функция получения списка 3-х животных с самой низкой ценой.
     * Переопределён из {@link AnimalRepository}
     *
     * @param animalList список животных
     * @return List - список имён, отсортированный в обратном алфавитном порядке
     */
    @Override
    public List<String> findMinConstAnimals(List<Animal> animalList) {
        return animalList.stream()
                .sorted(Comparator.comparing(Animal::getCost))
                .limit(3)
                .map(Animal::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    private double findAnimalAge(Animal animal) {
        return Period.between(animal.getBirthDate(), LocalDate.now()).getYears();
    }

    String convertToJson(Set<Animal> animalList) throws IOException {
        SerializeUtil serializeUtil = new SerializeUtil();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("SerializerService");
        module.addSerializer(Animal.class, serializeUtil);
        mapper.registerModule(module);
        return mapper.writeValueAsString(animalList);
    }
}

