package com.ripple.etl.executor;

import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class KafkaExecutorFilter implements ExecutorFilter {

    /**
     *
     * @param recipes all the recipes we get from kafka topic
     * @param recipeName recipe name we need to filter the recipes with.
     * @return a list that contains this specific recipe only.
     */
    @Override
    public List<Recipe> filter(List<Recipe> recipes, String recipeName) {
        log.info("filtering dataset using recipe name, if it's passed through the docker-compose.yml file");
        return recipes.stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .collect(Collectors.toList());
        // when we make a stream of bytes and we need to convert it to a list again we will use collect method, to collect bytes into
        //      a list that we can access again. While stream give us access to a lot of methods as filter, map, flatMap
    }
}
