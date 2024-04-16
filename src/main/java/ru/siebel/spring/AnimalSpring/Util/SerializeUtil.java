package ru.siebel.spring.AnimalSpring.Util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.siebel.spring.AnimalSpring.Api.Model.Animal;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class SerializeUtil extends JsonSerializer<Animal> {
    public SerializeUtil() {
    }

    public void serialize(Animal animal, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("breed", animal.getBreed());
        jsonGenerator.writeStringField("name", animal.getName());
        jsonGenerator.writeNumberField("cost", animal.getCost());
        jsonGenerator.writeStringField("character", animal.getCharacter());
        jsonGenerator.writeStringField("birthDay", animal.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        jsonGenerator.writeStringField("secretInformation", Base64.getEncoder().encodeToString(animal.getSecretInformation().getBytes()));
        jsonGenerator.writeEndObject();
    }
}