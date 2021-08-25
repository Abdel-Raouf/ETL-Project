package com.ripple.etl.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

// this lombok annotation generates: private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class); field
//  which prevent me from repeating myself every time i need to log something using any custom logger as log4j, log4j2, or any logger else.
@Slf4j
// indicates that the class can be used by the Spring IoC container as a source of bean definitions.
@Configuration
public class SchedulerConfiguration {
    // constructor based dependency injection
    // The design principle of Inversion of Control emphasizes keeping the Java classes independent of each other
    //  and the container frees them from object creation and maintenance.

    // The Spring @Bean annotation is usually declared in configuration class methods

    // When we need to create multiple beans of the same type in the same Spring context,
    //  we can give custom names to the beans and refer to them using those names.
    // I named the bean to be able to inject it specifically using it's name later in the @Qualifier annotation.
    @Bean(name = "RecipeTransformersList")
    List<RecipeTransformer> recipeTransformers(
            RecipeDifficultyTransformer difficultyTransformer,
            RecipeTimeNormalizationTransformer recipeTimeNormalizationTransformer
    ) {
        log.info("passing our list of transformers to an array list to be able to apply them in order on our data.");
        List<RecipeTransformer> list = new ArrayList<>();
        list.add(recipeTimeNormalizationTransformer);
        list.add(difficultyTransformer);

        return list;
    }
}
