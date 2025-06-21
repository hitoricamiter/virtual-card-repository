package ru.zaikin.feignmock;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.zaikin.dto.MyEvent;

@FeignClient(name = "self-client", url = "http://localhost:8085")
public interface EventClient {

    @PostMapping("/kafka/send")
    ResponseEntity<String> sendObject(MyEvent event);

}

