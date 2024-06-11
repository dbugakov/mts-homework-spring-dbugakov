package ru.siebel.spring.AnimalSpring;

import Config.TestNegativeServiceConfig;
import Config.TestPositiveServiceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.siebel.spring.AnimalSpring.Repository.AnimalRepositoryImpl;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({TestNegativeServiceConfig.class, TestPositiveServiceConfig.class})
@ActiveProfiles({"test"})
@TestPropertySource("/test.properties")
class AnimalSpringApplicationTests {
    @Autowired
    private AnimalRepositoryImpl animalRepository;
    @Autowired(required = false)
    @Qualifier("negativeServiceForTests")
    private CreateAnimalServiceImpl negativeAnimalServiceTest;
    @Autowired
    @Qualifier("positiveServiceForTests")
    private CreateAnimalServiceImpl positiveAnimalServiceTest;

    @Test
    void contextLoadsPositive() {
        assertNotNull(animalRepository);
        assertNotNull(positiveAnimalServiceTest);
    }

    @Test
    void contextLoadsNegative() {
        assertNull(negativeAnimalServiceTest);
    }

    @Test
    void createAnimalsPositive() {
        assertEquals(4, animalRepository.getAnimalMap().size());
    }

    @Test
    void createAnimalsNegative() {
        assertNotEquals(10, animalRepository.getAnimalMap().size());
    }

}
