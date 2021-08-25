package com.ripple.etl;

import com.ripple.etl.scheduler.InvalidSourceException;

import java.io.FileNotFoundException;

public interface SchedulerService {
    void schedule(String source) throws FileNotFoundException, InvalidSourceException;
}
