package com.ripple.etl.scheduler;

import com.ripple.etl.AlertService;
import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Arrays;

@Slf4j
@Service
public class KafkaSchedulerWriter implements SchedulerWriter {

    @Value("${kafka.template.default-topic}")
    private String topicName;

    private KafkaTemplate<String, Recipe> kafkaTemplate;

    @Override
    public Boolean write(Recipe recipe) {


        ListenableFuture<SendResult<String, Recipe>> future =
                kafkaTemplate.send(topicName, recipe);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Recipe>>() {
            private AlertService alertService;

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Unable to send Recipe to kafka due to", throwable);
                this.alertService.sendAlert(throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Recipe> stringRecipeSendResult) {
                log.info("message sent with offset ", stringRecipeSendResult.getRecordMetadata().offset());
            }
        });

        return false;
    }
}
