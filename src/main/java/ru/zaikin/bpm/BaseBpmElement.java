package ru.zaikin.bpm;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.zaikin.enumeration.BpmElement;
import ru.zaikin.enumeration.BpmLogActivityType;
import ru.zaikin.exception.BpmTaskActivityException;
import ru.zaikin.util.BaseBpmUtils;

import static ru.zaikin.enumeration.BpmElement.DELEGATE;
import static ru.zaikin.enumeration.BpmElement.LISTENER;
import static ru.zaikin.enumeration.BpmLogActivityType.*;


@NoArgsConstructor
@Slf4j
public abstract class BaseBpmElement implements JavaDelegate, ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        logProcess(LISTENER, execution);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logProcess(DELEGATE, execution);
    }

    private void logProcess(BpmElement element, DelegateExecution execution) {
        this.logAction(element, START, execution);
        try {
            this.perform(execution);
        } catch (BpmnError e) {
            logError(element, execution, e);
            throw new BpmnError(e.getErrorCode(), e.getMessage(), e);
        } catch (Exception e) {
            logError(element, execution, e);
            throw new BpmTaskActivityException(execution, e.getCause(), e.getMessage(),
                    BaseBpmUtils.getStackTrace(e.getStackTrace()));
        }
        this.logAction(element, COMPLETE, execution);
    }

    public abstract void perform(DelegateExecution delegateExecution);

    private void logAction(BpmElement element, BpmLogActivityType activityAction, DelegateExecution execution) {
        log.debug(
                "{} {} execute: businessKey={}, processInstanceId={}, activityId={}, activityInstanceId={}," +
                        " processDefinitionId={}",
                element.name(), activityAction, execution.getBusinessKey(),
                execution.getProcessInstanceId(), execution.getCurrentActivityId(),
                execution.getActivityInstanceId(), execution.getProcessDefinitionId());
    }

    private void logError(BpmElement element, DelegateExecution execution, Exception e) {
        log.error(
                "{} {} execute: businessKey={}, processInstanceId={}, activityId={}, activityInstanceId={}," +
                        " processDefinitionId={}, message={}",
                element.name(), ERROR, execution.getBusinessKey(),
                execution.getProcessInstanceId(), execution.getCurrentActivityId(),
                execution.getActivityInstanceId(), execution.getProcessDefinitionId(),
                e.getMessage());
    }
}
