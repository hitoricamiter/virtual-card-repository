package ru.zaikin.bpm.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import ru.zaikin.bpm.BaseBpmElement;
import ru.zaikin.dto.Application;
import ru.zaikin.dto.ApplicationEvent;

import static ru.zaikin.enumeration.ProcessVariable.APPLICATION_EVENT;
import static ru.zaikin.enumeration.ProcessVariable.APPLICATION_JSON;
import static ru.zaikin.util.MapperUtil.jsonToObject;

@Slf4j
@RequiredArgsConstructor
@Component("startProcess")
public class StartProcessListener extends BaseBpmElement {

    @Override
    public void perform(DelegateExecution delegateExecution) {
        String applicationId = delegateExecution.getProcessBusinessKey();
        ApplicationEvent applicationEvent;
        try {
            applicationEvent = (ApplicationEvent) delegateExecution.getVariable(APPLICATION_EVENT);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при парсинге сообщения по заявке id = " + applicationId, e);
        }
        delegateExecution.setVariable(APPLICATION_JSON, applicationEvent.getApplicationJson());

    }
}
