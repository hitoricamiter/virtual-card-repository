package ru.zaikin.exception;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.util.List;

public class BpmTaskActivityException extends RuntimeException {
    private static final String MESSAGE = "%n%ndelegate/listener execute with exception: %n- businessKey=%s, %n- processInstanceId=%s, %n- activityId=%s, %n- activityInstanceId=%s, %n- exceptionMessage=[%s], %n%n- stackTrace=[%n%s%n]%n%n";

    public BpmTaskActivityException(DelegateExecution execution, Throwable cause, String exceptionMessage, List<String> stackTrace) {
        super(String.format("%n%ndelegate/listener execute with exception: %n- businessKey=%s, %n- processInstanceId=%s, %n- activityId=%s, %n- activityInstanceId=%s, %n- exceptionMessage=[%s], %n%n- stackTrace=[%n%s%n]%n%n", execution.getBusinessKey(), execution.getProcessInstanceId(), execution.getCurrentActivityId(), execution.getActivityInstanceId(), exceptionMessage, stackTrace), cause);
    }
}