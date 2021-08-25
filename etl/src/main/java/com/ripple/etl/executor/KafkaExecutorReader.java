package com.ripple.etl.executor;

import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KafkaExecutorReader implements ExecutorReader {

//    List<Recipe> recipes = null;

    /**
     *
     * @param recipes list of recipes we get from kafka, when we listen to consume a specific topic.
     * @return the whole recipes we get from kafka topic we listened to.
     */

    @KafkaListener(
            topics = "recipe-topic",
            containerFactory = "recipeKafkaListenerContainerFactory")
    public List<Recipe> readRecipeListener(List<Recipe> recipes) {
        log.info("listening to kafka to get our list of recipes");
        return recipes;
    }
}
