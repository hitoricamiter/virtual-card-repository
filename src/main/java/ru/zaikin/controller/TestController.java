package ru.zaikin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zaikin.dto.MyEvent;
import ru.zaikin.feignmock.EventClient;
import ru.zaikin.service.KafkaProducerService;

@RestController
@RequestMapping("/kafka")
public class TestController {

    private final KafkaProducerService kafkaProducerService;
    private final EventClient eventClient;

    public TestController(KafkaProducerService kafkaProducerService, EventClient eventClient) {
        this.kafkaProducerService = kafkaProducerService;
        this.eventClient = eventClient;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendObject(@RequestBody MyEvent event) {
        kafkaProducerService.sendObject(event);

        return ResponseEntity.ok("ok");
    }


}
