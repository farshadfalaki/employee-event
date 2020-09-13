package com.takeaway.challenge.event;

import com.takeaway.challenge.model.Outbox;
import com.takeaway.challenge.service.OutboxService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;
@Component
@AllArgsConstructor
@Slf4j
public class ProducerSenderCallbackImpl implements ListenableFutureCallback<SendResult<String, Outbox>> {
    private final OutboxService outboxService;
    @Override
    public void onFailure(Throwable ex) {
        log.warn("onFailure : {} " , ex);
    }

    @Override
    public void onSuccess(SendResult<String, Outbox> result) {
        log.info("onSuccess result : {}" , result);
        if(result.getProducerRecord()!=null){
            outboxService.markAsPublished(result.getProducerRecord().value());
        }else{
            log.warn("onSuccess result null ProducerRecord!!");
        }
    }
}
