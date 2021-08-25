package com.ripple.etl.scheduler;

import com.ripple.etl.common.model.Recipe;

import java.util.List;

public interface SchedulerReader {
    List<Recipe> read(String source) throws InvalidSourceException;
}
