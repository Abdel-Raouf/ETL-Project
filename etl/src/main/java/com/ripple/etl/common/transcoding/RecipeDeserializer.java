package com.ripple.etl.common.transcoding;

import com.ripple.etl.common.model.Recipe;

public interface RecipeDeserializer {
    Recipe deserialize(String data) throws RecipeDeserializationException;
}
