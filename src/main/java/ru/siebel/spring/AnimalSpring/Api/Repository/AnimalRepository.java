package ru.siebel.spring.AnimalSpring.Api.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.siebel.spring.AnimalSpring.Model.Animal;
import ru.siebel.spring.AnimalSpring.Model.AnimalType;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> getAnimalByName(String name);

    Optional<Animal> getAnimalById(Long aLong);
    @Query("FROM AnimalType WHERE name = ?1")
    List<AnimalType> getAnimalTypeByName(String name);

    @Query(value = "SELECT avg(extract(year from age(birth_date))) FROM public.animal", nativeQuery = true)
    double findAverageAge();

    @Query(value = "SELECT name FROM public.animal" +
            "where extract(year from age(birth_date)) > 5" +
            "and cost > (select avg(cost) FROM public.animal)", nativeQuery = true)
    List<String> findOldAndExpensive();

    @Query(value = "select name from public.animal" +
            "where id in" +
            "(SELECT id FROM public.animal" +
            "ORDER BY cost ASC" +
            "limit 3)" +
            "ORDER BY name DESC", nativeQuery = true)
    List<String> findMinCostAnimals();
}
