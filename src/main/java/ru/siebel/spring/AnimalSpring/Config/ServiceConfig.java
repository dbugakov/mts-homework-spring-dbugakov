package ru.siebel.spring.AnimalSpring.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.siebel.spring.AnimalSpring.Service.CreateAnimalServiceImpl;

@Configuration
public class ServiceConfig {

    @Bean(name = "serviceFromConfig")
    @Scope("prototype")
    public CreateAnimalServiceImpl CreateAnimalServiceImpl() {
        return new CreateAnimalServiceImpl();
    }
}
