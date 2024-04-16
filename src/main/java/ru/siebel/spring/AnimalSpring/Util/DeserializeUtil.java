package ru.siebel.spring.AnimalSpring.Util;



import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import ru.siebel.spring.AnimalSpring.Api.Model.Animal;
import ru.siebel.spring.AnimalSpring.Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DeserializeUtil extends JsonDeserializer<List<Animal>> {
    public DeserializeUtil() {
    }

    @Override
    public List<Animal> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        List<Animal> animals = new ArrayList<>();
        Animal animal = null;
        String breed;
        while (!jsonParser.isClosed()) {
            JsonToken jsonToken = jsonParser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = jsonParser.getCurrentName();
                jsonParser.nextToken();
                if ("breed".equals(fieldName)) {
                    if (animal != null) animals.add(animal);
                    breed = jsonParser.getValueAsString();
                    switch (breed) {
                        case "Cat":
                            animal = new Cat();
                            break;
                        case "Dog":
                            animal = new Dog();
                            break;
                        case "Shark":
                            animal = new Shark();
                            break;
                        case "Wolf":
                            animal = new Wolf();
                            break;
                    }
                    animal.setBreed(jsonParser.getValueAsString());
                } else switch (fieldName) {
                    case "name":
                        animal.setName(jsonParser.getValueAsString());
                        break;
                    case "cost":
                        animal.setCost(jsonParser.getValueAsDouble());
                        break;
                    case "birthDay":
                        animal.setBirthDate(LocalDate.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        break;
                    case "character":
                        animal.setCharacter(jsonParser.getValueAsString());
                        break;
                    case "secretInformation":
                        animal.setSecretInformation(new String(Base64.getDecoder().decode(jsonParser.getValueAsString())));
                        break;
                }
            }
        }
        return animals;
    }
}