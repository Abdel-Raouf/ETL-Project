package com.ripple.etl.scheduler;

import com.ripple.etl.common.model.Recipe;

public interface RecipeTransformer {
    Recipe transform(Recipe recipe);
}
