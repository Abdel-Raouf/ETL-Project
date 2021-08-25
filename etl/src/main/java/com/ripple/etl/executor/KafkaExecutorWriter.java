package com.ripple.etl.executor;

import com.ripple.etl.common.model.Recipe;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Slf4j
@Component
public class KafkaExecutorWriter implements ExecutorWriter {
    private final RecipeRepository recipeRepository;

// TODO: better to use JDBC here, as Hibernate is an overkill solution.

    /**
     * @param recipes List of recipes we will write to the database
     */
    @Override
    public void write(List<Recipe> recipes) {

        log.info("writing recipes list to postgresql through hibernate");
        recipeRepository.save(recipes);
    }
}
