package com.ripple.etl.scheduler;

import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RecipeDifficultyTransformer implements RecipeTransformer {

    // I used  static and final together to create a constant "DIFFICULT_INGREDIENT" as to deal with the problem of magic numbers in linear algebra.
    // as the name we search with as "beef" here, in a while i will forget it's meaning to the application logic,
    // which is the default ingredient i want to include in our dataset (that's a unique value with unexplained meaning).
    public static final String DIFFICULT_INGREDIENT = "beef";

    @Override
    public Recipe transform(Recipe recipe) {

        if(recipe.getIngredients().toLowerCase().contains(DIFFICULT_INGREDIENT)){
            log.info("apply time(cookTime, prepTime) transformation");

            int addition = Integer.parseInt(recipe.getPrepTime().replaceAll("[^0-9]", "")) + Integer.parseInt(recipe.getCookTime().replaceAll("[^0-9]", ""));
            int prepDuration = Integer.parseInt(recipe.getPrepTime().replaceAll("[^0-9]", ""));
            int cookDuration = Integer.parseInt(recipe.getCookTime().replaceAll("[^0-9]", ""));

            if( (recipe.getPrepTime().contains("H") && recipe.getCookTime().contains("H")) ||
                    ( (recipe.getPrepTime().contains("H") && recipe.getCookTime().contains("M")) && cookDuration > 0 ) ||
                    ( (recipe.getCookTime().contains("H") && recipe.getPrepTime().contains("M")) && prepDuration > 0 ) ||
                    ( (recipe.getPrepTime().contains("M") && recipe.getCookTime().contains("M")) && (addition > 60) ) ){
                recipe.setDifficulty("Hard");
            }else if( ( (recipe.getPrepTime().contains("M") && recipe.getCookTime().contains("M")) && (addition >= 30 && addition <= 60) ) ||
                    ( (recipe.getPrepTime().contains("H") && prepDuration == 1) && ( cookDuration == 0) ) ||
                    ( (recipe.getCookTime().contains("H") && cookDuration == 1) && ( prepDuration == 0) ) ) {
                recipe.setDifficulty("Medium");
            } else if( ( (recipe.getPrepTime().contains("M") && recipe.getCookTime().contains("M")) && (addition < 30 )) )  {
                recipe.setDifficulty("Easy");
            }
        } else {
            recipe.setDifficulty("Unknown");
        }

        return recipe;
    }
}
