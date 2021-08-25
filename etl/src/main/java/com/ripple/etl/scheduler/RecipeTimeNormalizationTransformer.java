package com.ripple.etl.scheduler;

import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeTimeNormalizationTransformer implements RecipeTransformer {
    @Override
    public Recipe transform(Recipe recipe) {
        log.info("Apply Data cleaning on cookTime and prepTime ");
        if (recipe.getPrepTime().replaceAll("[^0-9]", "").isEmpty()) {
            recipe.getPrepTime().concat("0M");
        }

        if (recipe.getCookTime().replaceAll("[^0-9]", "").isEmpty()) {
            recipe.getCookTime().concat("0M");
        }

        return recipe;
    }
}
