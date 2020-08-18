package ru.omsk.neoLab.answer.Serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.omsk.neoLab.answer.Answer;

public class AnswerDeserialize {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Answer deserialize(String jsonAnswer) throws JsonProcessingException {
        return mapper.readValue(jsonAnswer, Answer.class);
    }
}
