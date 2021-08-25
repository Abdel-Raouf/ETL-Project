package com.ripple.etl.executor;

import com.ripple.etl.common.model.Recipe;

import java.util.List;

public interface ExecutorWriter {
    void write(List<Recipe> recipes);
}
