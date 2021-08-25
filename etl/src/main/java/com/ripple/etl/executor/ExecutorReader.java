package com.ripple.etl.executor;

import com.ripple.etl.common.model.Recipe;

import java.util.List;

public interface ExecutorReader {
    List<Recipe> readRecipeListener(List<Recipe> recipe);
}
