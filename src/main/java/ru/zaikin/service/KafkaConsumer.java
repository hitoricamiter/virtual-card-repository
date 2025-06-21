package ru.zaikin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import ru.zaikin.dto.ApplicationEvent;
import ru.zaikin.enumeration.ProcessVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final RuntimeService runtimeService;
    private static final String RECEIVER = "receiver";
    private final String PROCESS_KEY = "PFK";


    @Bean
    public Consumer<Message<ApplicationEvent>> consumeApplicationEvent() {
        return msg -> {
            var acknowledgment = msg.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            log.info("Received from Kafka {}", msg.getPayload());
            String messageReceiver = msg.getHeaders().get("Message-Receiver", String.class);

            if (RECEIVER.equals(messageReceiver)) {
                startProcess(msg.getPayload());
            } else {
                log.info("Received unknown message {}", msg.getPayload());
            }

            if (acknowledgment != null) acknowledgment.acknowledge();
            startProcess(msg.getPayload());
        };
    }

    private void startProcess(ApplicationEvent event) {
        VariableMap variableMap = Variables.createVariables();
        ObjectValue applicationEventVar = Variables.objectValue(event)
                .serializationDataFormat(MediaType.APPLICATION_JSON_VALUE)
                .create();
        variableMap.putValueTyped(ProcessVariable.APPLICATION_EVENT, applicationEventVar);

        String id = runtimeService.startProcessInstanceByKey(PROCESS_KEY, event.getId().toString(), variableMap).getId();
        log.info("Started PFK process with id {}", id);

    }

}