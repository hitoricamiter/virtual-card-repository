package ru.zaikin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperUtil {
    public static <T> T jsonToObject(String json,
                                     Class<T> objectType,
                                     ObjectMapper objectMapper,
                                     String applicationId) {
        try {
            return objectMapper.readValue(json, objectType);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при парсинге объекта в {}", objectType.getSimpleName());
            throw new RuntimeException(e);
        }
    }
}
