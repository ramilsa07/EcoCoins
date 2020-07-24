package ru.omsk.neoLab.race.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.race.ARace;

public class RaceSerializer {

    public static String serialize(final ARace race) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(race);
    }
}
