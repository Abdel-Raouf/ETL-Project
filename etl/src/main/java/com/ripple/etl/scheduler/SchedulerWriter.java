package com.ripple.etl.scheduler;

import com.ripple.etl.common.model.Recipe;

public interface SchedulerWriter {
    Boolean write(Recipe recipe);
}
