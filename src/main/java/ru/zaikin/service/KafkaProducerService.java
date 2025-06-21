package ru.zaikin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import ru.zaikin.dto.MyEvent;

@Service
public class KafkaProducerService {

    private final StreamBridge streamBridge;

    @Autowired
    public KafkaProducerService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendMessage(String message) {
        streamBridge.send("stringProducer-out-0", message);
    }

    public void sendObject(MyEvent event) {
        streamBridge.send("myProducer-out-0", event);
    }

}
