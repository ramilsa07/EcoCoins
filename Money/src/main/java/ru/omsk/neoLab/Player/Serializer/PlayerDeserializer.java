package ru.omsk.neoLab.Player.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.Player.Player;

public class PlayerDeserializer {

    public static Player deserialize(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Player.class);
    }
}
