package com.ripple.etl.executor;

import com.ripple.etl.common.model.Recipe;

import java.util.List;

public interface ExecutorFilter {
    List<Recipe> filter(List<Recipe> recipes, String recipeName);
}
