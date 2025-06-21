package ru.zaikin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {
    private UUID id;
    private String number;
    private LocalDateTime createDate;
    private String status;
    private String passport;
    private List<Document> documents = new ArrayList<>();
    private String stage;




}
