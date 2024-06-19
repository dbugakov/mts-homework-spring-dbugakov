package ru.siebel.spring.AnimalSpring.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.siebel.spring.AnimalSpring.Util.FileUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс Animal.
 *
 * @author Бугаков Данил
 * @version 1.1
 */
@Data
@Entity
@NoArgsConstructor
public class Animal implements Serializable, Comparable<Animal> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Поле порода
     */
    @ManyToOne
    @JoinColumn(name = "animal_type_id", nullable = false)
    protected AnimalType breed;

    /**
     * Поле имя
     */
    protected String name;

    /**
     * Поле цена
     */
    protected Double cost;

    /**
     * Поле характер
     */
    protected String character;

    /**
     * Поле день рождения животного, в формате dd-MM-yyyy
     */
    protected LocalDate birthDate;

    public String secretInformation = FileUtil.getRandomLineFromFile("src/main/resources/secretStore/secretInformation.txt");

    public Animal(String name, Double cost, String character, LocalDate birthDate) {
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
    }

    /**
     * Переопределение функции equals из {@link Object}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal that = (Animal) o;
        return Objects.equals(name, that.name);
    }

    /**
     * Переопределение функции hashCode из {@link Object}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(breed, name, cost, character, birthDate);
    }

    /**
     * Реализация функции compareTo {@link Comparable}.
     */
    @Override
    public int compareTo(Animal o) {
        return getBirthDate().compareTo(o.getBirthDate());
    }
}
