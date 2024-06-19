package ru.siebel.spring.AnimalSpring.Api.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.siebel.spring.AnimalSpring.Model.Animal;
import ru.siebel.spring.AnimalSpring.Model.AnimalType;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("FROM Animal WHERE name = ?1")
    List<Animal> getAnimalsByName(String name);

    @Override
    @Query("FROM Animal WHERE id = ?1")
    Animal getById(Long aLong);

    @Query("FROM AnimalType WHERE name = ?1")
    List<AnimalType> getAnimalTypeByName(String name);

    @Query(value = "SELECT avg(extract(year from age(birth_date))) FROM public.animal", nativeQuery = true)
    double findAverageAge();

    @Query(value = "SELECT name FROM public.animal\n" +
            "where extract(year from age(birth_date)) > 5\n" +
            "and cost > (select avg(cost) FROM public.animal)", nativeQuery = true)
    List<String> findOldAndExpensive();

    @Query(value = "select name from public.animal\n" +
            "\twhere id in\n" +
            "\t\t(SELECT id FROM public.animal\n" +
            "\t\tORDER BY cost ASC\n" +
            "\t\tlimit 3)\n" +
            "\tORDER BY name DESC", nativeQuery = true)
    List<String> findMinCostAnimals();
}
