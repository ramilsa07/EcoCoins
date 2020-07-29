package ru.omsk.neoLab.board.Сell.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Сell.Cell;

public class CellSerializer {

    public static String serialize(final Cell cell) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cell);
    }
}
