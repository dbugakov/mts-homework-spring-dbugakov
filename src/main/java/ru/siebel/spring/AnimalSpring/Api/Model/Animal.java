package ru.siebel.spring.AnimalSpring.Api.Model;
import ru.siebel.spring.AnimalSpring.Model.AbstractAnimal;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Animal, реализован в {@link AbstractAnimal}.
 */
public interface Animal extends Comparable<Animal>, Serializable {

    /**
     * Функция получения строки.
     *
     * @return возвращает строку
     */
    String getBreed();

    /**
     * Функция получения строки.
     *
     * @return возвращает строку
     */
    String getName();

    /**
     * Функция получения значения с плавающей точкой.
     *
     * @return возвращает значение с плавающей точкой
     */
    Double getCost();

    /**
     * Функция получения строки.
     *
     * @return возвращает строку
     */
    String getCharacter();

    /**
     * Функция получения значения типа LocalDate.
     *
     * @return возвращает LocalDate
     */
    LocalDate getBirthDate();

    String getSecretInformation();

    void setBreed(String breed);

    void setName(String name);

    void setCost(Double cost);

    void setCharacter(String character);

    void setBirthDate(LocalDate birthDate);

    void setSecretInformation(String secretInformation);

}
