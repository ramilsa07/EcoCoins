package ru.omsk.neoLab.answer.Serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.answer.Answer;

public class AnswerSerialize {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String serialize(Answer answer) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer);
    }
}
