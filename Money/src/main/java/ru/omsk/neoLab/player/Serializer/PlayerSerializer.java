package ru.omsk.neoLab.player.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.player.Player;

public class PlayerSerializer {

    public static String serialize(final Player player) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(player);
    }
}
