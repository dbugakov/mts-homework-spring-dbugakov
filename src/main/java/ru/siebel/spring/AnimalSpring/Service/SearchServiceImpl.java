package ru.siebel.spring.AnimalSpring.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.siebel.spring.AnimalSpring.Api.Repository.AnimalRepository;
import ru.siebel.spring.AnimalSpring.Api.Service.SearchService;
import ru.siebel.spring.AnimalSpring.Exception.InvalidAnimalBirthDateException;
import ru.siebel.spring.AnimalSpring.Exception.InvalidAnimalException;
import ru.siebel.spring.AnimalSpring.Model.Animal;
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
 * Класс SearchServiceImpl, реализует {@link SearchService}.
 */
@Service
@Data
@RequiredArgsConstructor
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private final AnimalRepository animalRepository;

    List<Animal> animalList;

    @PostConstruct
    private void init() throws IOException {
        System.out.println("getAnimalByName");
        System.out.println(getAnimalByName(" Шарик"));
        System.out.println("findDuplicate");
        System.out.println(findDuplicate());
        System.out.println("findMinCostAnimals");
        System.out.println(findMinCostAnimals());
        System.out.println("findAverageAge");
        System.out.println(findAverageAge());
    }

    public List<Animal> getAnimalByName(String name) {
        return animalRepository.getAnimalByName(name);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Функция checkLeapYearAnimalById
     *
     * @return Является ли год рождения животного високосный
     */
    @Override
    public boolean checkLeapYearAnimalById(Long animalId) throws InvalidAnimalBirthDateException {
        Optional<Animal> animal = animalRepository.getAnimalById(animalId);
        String outputMessage;
        boolean result;
        if (animal.isPresent()) {
            LocalDate animalBirthDate = animal.get().getBirthDate();
            if (animalBirthDate != null) {
                outputMessage = animal.get().getName();

                if (animalBirthDate.isLeapYear()) {
                    outputMessage += " был рожден в високосный год";
                    result = true;
                } else {
                    outputMessage += " не был рожден в високосный год";
                    result = false;
                }
                System.out.println(outputMessage);
            } else
                throw new InvalidAnimalBirthDateException("У животного " + animal.get().getName() + " не указана дата его рождения!");
        } else throw new InvalidAnimalException("На вход пришло некорректный объект животного! " + LocalDate.now());
        return result;
    }

    /**
     * Функция получения HashMap с животными, которые родились в високосный год
     * Переопределён из {@link AnimalRepository}
     *
     * @return HashMap, где ключ - название класса + поле name, значение - поле дата рождения
     */
    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        animalList = getAllAnimals();
        return animalList.stream()
                .filter(animal -> {
                    try {
                        return checkLeapYearAnimalById(animal.getId());
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
     * @param age целевой возраст
     * @return HashMap, где ключ - экземпляр животного, значение - возраст животного
     */
    @Override
    public Map<Animal, Integer> findOlderAnimal(int age) throws IOException {
        animalList = getAllAnimals();
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
     * @return HashMap, где ключ - название класса, значение - список дубликатов
     */
    @Override
    public Map<String, List<Animal>> findDuplicate() {
        animalList = getAllAnimals();
        return animalList.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .values().stream()
                .filter(animals -> animals.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(animal -> animal.getBreed().getName()));
    }

    /**
     * Функция получения списка 3-х животных с самой низкой ценой.
     * Переопределён из {@link AnimalRepository}
     *
     * @return List - список имён, отсортированный в обратном алфавитном порядке
     */
    @Override
    public List<String> findMinCostAnimals() {
        return animalRepository.findMinCostAnimals();
    }

    /**
     * Функция получения среднего возраста из списка животных.
     * Ничего не возвращает, печатает на экран.
     * Переопределён из {@link AnimalRepository}
     */
    @Override
    public double findAverageAge() {
        return animalRepository.findAverageAge();
    }

    /**
     * Функция получения списка с животными, возраст которых >5 лет и стоимость выше средней.
     * Переопределён из {@link AnimalRepository}
     *
     * @return List - список имён, отсортированный по дате рождения (по возрастанию)
     */
    @Override
    public List<String> findOldAndExpensive() {
        return animalRepository.findOldAndExpensive();
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
