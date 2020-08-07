package ru.omsk.neoLab.Race.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.Race.ARace;

public class RaceDeserializer {

    public static ARace deserialize(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ARace.class);
    }
}
