package ru.siebel.spring.AnimalSpring.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ResultReader {

    public static void printDeserializeAnimal() throws FileNotFoundException, JsonProcessingException {
        File secret = new File("Animals/src/main/resources/results/findOlderAnimals.json");
        Scanner scanner = new Scanner(secret);
        DeserializeUtil animalDeserializer = new DeserializeUtil();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        SimpleModule module = new SimpleModule("DeserializeUtil");
        module.addDeserializer(List.class, animalDeserializer);
        mapper.registerModule(module);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        while (scanner.hasNextLine()) {
            System.out.println("Deserialize animal: " + mapper.readValue(scanner.nextLine(), List.class));
        }
    }

    public static void printFileCntAllLines(String path) {
        System.out.println("Количество строк в файле " + path + " = " + FileUtil.fileChannelReadAllLines(path).size());
    }


}
