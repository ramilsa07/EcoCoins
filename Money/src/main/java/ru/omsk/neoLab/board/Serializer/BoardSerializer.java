package ru.omsk.neoLab.board.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.board.Board;

public class BoardSerializer {

    final static ObjectMapper mapper = new ObjectMapper();

    public static String serialize(final Board board) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(board);
    }
}
