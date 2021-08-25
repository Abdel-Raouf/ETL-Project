package com.ripple.etl.executor;

import com.ripple.etl.ExecutorService;
import com.ripple.etl.common.model.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor // using lombok we generates a constructor with 1 parameter for each field that requires special handling.
public class KafkaExecutorService implements ExecutorService {

    private final ExecutorReader executorReader;
    private final ExecutorFilter executorFilter;
    private final ExecutorWriter executorWriter;
    private List<Recipe> recipe;


    /**
     * Execute tasks and filter for the optional recipeName
     *
     * @optionalParam recipeName to filter the recipes with, if it is passed.
     */
    @Override
    public void Execute(Optional<String> recipeName) {
        log.info("executorService collect our functionalities");

        List<Recipe> recipes = this.executorReader.readRecipeListener(recipe);
        log.info("read recipes from kafka", recipes);
        if (recipeName.isPresent()) {
            log.debug("filter applied, as a result we will return the recipe we asked for only");
            recipes = this.executorFilter.filter(recipes, recipeName.get());
        }
        log.info("write recipes to database");
        this.executorWriter.write(recipes);
    }
}
