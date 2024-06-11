package Config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;

@TestConfiguration
@ActiveProfiles("test")
public class TestPositiveServiceConfig {

    @Bean(name = "positiveServiceForTests")
    @Scope("prototype")
    public CreateAnimalServiceImpl CreateAnimalServiceImpl() {
        return new CreateAnimalServiceImpl();
    }
}
