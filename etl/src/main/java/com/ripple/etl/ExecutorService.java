package com.ripple.etl;

import java.util.Optional;

public interface ExecutorService {
    /**
     * Execute all tasks, except when there is a filter exists.
     * @param recipeName optional recipe name to filter with.
     */
    void Execute(Optional<String> recipeName);
}
