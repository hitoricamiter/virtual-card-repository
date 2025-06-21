package ru.zaikin.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.zaikin.dto.MyEvent;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {

    @Bean
    public Consumer<MyEvent> myConsumer() {
        return event -> {
            System.out.println("📥 Получено сообщение из Kafka: " + event);
        };
    }
}