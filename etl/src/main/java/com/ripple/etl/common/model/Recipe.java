package com.ripple.etl.common.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data // is a convenient shortcut annotation that bundles the features of @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor together:
// Note: @Data -> doesn't create setter for final variables.
@Builder // using lombok to build complex objects, without writing all the boilerplate code.
public class Recipe {
    // variable declared with final keyword means, it cannot be reassigned it's reference(for classes as String class)
    // to a new value, which make the variable immutable and thread safe when publishing to threads.
    // for primitive types we cannot change it's value or reassign it's reference at all.
    private final String name;
    private final Instant createdAt;
    private final String ingredients;
    private final String url;
    private final String image;
    private final String cookTime;
    private final String recipeYield;
    private final String datePublished;
    private final String prepTime;
    private final String description;
    private String difficulty; // i will calculate it so, i can't declare it to be final.

    // Instant library is more precise as it's a UTC time, not a machine related time.
    public Instant getCreatedAt() {
        return Instant.now();
    }

}
