package com.ripple.etl.common.transcoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONRecipeTranscoder implements RecipeDeserializer {
    private ObjectMapper mapper;
    // dependency injection doesn't apply on custom types
    public JSONRecipeTranscoder() {
        this.mapper = new ObjectMapper();
    }

    /**
     * @param data each line we get from the recipes file, that we need to deserialize(from json to string).
     * @return each line we deserialize(from json to string), to be able to add it to the Recipe list
     * @throws RecipeDeserializationException
     */
    @Override
    public Recipe deserialize(String data) throws RecipeDeserializationException {
        try {
            log.info("getting each line in the dataset to apply deserialize on it using jackson");
            return this.mapper.readValue(data, Recipe.class);
        } catch (JsonProcessingException e) {
            log.debug("can't process the line in hand", e);
            throw new RecipeDeserializationException();
        }
    }
}
