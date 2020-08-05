package ru.omsk.neoLab.Answer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnswerDeserializer {

    public static Answer deserialize(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Answer.class);
    }
}
