package ru.siebel.spring.AnimalSpring.Service;

import org.springframework.stereotype.Component;
import ru.siebel.spring.AnimalSpring.Api.Model.Animal;
import ru.siebel.spring.AnimalSpring.Api.Service.SearchService;
import ru.siebel.spring.AnimalSpring.Exception.*;
import java.time.LocalDate;

/**
 * Класс SearchServiceImpl, реализует {@link SearchService}.
 */
public class SearchServiceImpl implements SearchService {

    /**
     * Функция checkLeapYearAnimal
     *
     * @return Является ли год рождения животного високосный
     */
    @Override
    public boolean checkLeapYearAnimal(Animal animal) throws InvalidAnimalBirthDateException {
        String outputMessage;
        boolean result;
        if (animal != null) {
            LocalDate animalBirthDate = animal.getBirthDate();
            if (animalBirthDate != null) {
                outputMessage = animal.getName();

                if (animalBirthDate.isLeapYear()) {
                    outputMessage += " был рожден в високосный год";
                    result = true;
                } else {
                    outputMessage += " не был рожден в високосный год";
                    result = false;
                }
                System.out.println(outputMessage);
            } else
                throw new InvalidAnimalBirthDateException("У животного " + animal.getBreed() + " не указана дата его рождения!");
        } else throw new InvalidAnimalException("На вход пришло некорректный объект животного! " + LocalDate.now());
        return result;
    }
}
