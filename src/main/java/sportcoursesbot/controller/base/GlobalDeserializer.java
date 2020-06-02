package sportcoursesbot.controller.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.function.Function;

public class GlobalDeserializer<T> {
    private Function<String, T> deserializer;

    private GlobalDeserializer(Function<String, T> deserializer) {
        this.deserializer = deserializer;
    }

    public GlobalDeserializer() {
        deserializer = new JsonDeserializer();
    }

    public Function<String, T> getDeserializer() {
        return deserializer;
    }

    public T deserialize(String from) {
        T apply = deserializer.apply(from);
        return apply;
    }

    private class JsonDeserializer implements Function<String, T> {
        private ObjectMapper objectMapper = new ObjectMapper();

        @SneakyThrows
        @Override
        public T apply(String s) {
            T value = objectMapper.readValue(s, new TypeReference<T>() {
            });
            return value;
        }
    }

}
