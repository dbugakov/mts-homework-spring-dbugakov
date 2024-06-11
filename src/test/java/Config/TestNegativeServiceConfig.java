package Config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;

@TestConfiguration
public class TestNegativeServiceConfig {

    @Bean(name = "negativeServiceForTests")
    @Scope("prototype")
    @Profile("non_test")
    public CreateAnimalServiceImpl CreateAnimalServiceImpl() {
        return new CreateAnimalServiceImpl();
    }
}
