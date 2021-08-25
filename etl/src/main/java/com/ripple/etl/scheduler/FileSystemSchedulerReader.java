package com.ripple.etl.scheduler;

import com.ripple.etl.common.model.Recipe;
import com.ripple.etl.common.transcoding.RecipeDeserializationException;
import com.ripple.etl.common.transcoding.RecipeDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileSystemSchedulerReader implements SchedulerReader {
    private final RecipeDeserializer sourceDeserializer;

    /**
     *
     * @param source Directory path we get from the docker-compose.yml
     * @throws InvalidSourceException exception to handle any problem arise when processing the source file path.
     */

    @Override
    public List<Recipe> read(String source) throws InvalidSourceException {
        List<Recipe> recipes = new ArrayList<>();

        log.info("reading dataset file/s from the directory path we get.");
        // read file system from a specific folder.
        // I need the path to the input folder as "d:/input_folder" in windows
        //      OR "/home/your_username/input_folder/" in ubuntu
        File dirPath = new File(source);
        File[] directoryListing = dirPath.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {

                // get the file path of the child file to extract JSON from it.
                Path filePath = null;
                try {
                    filePath = ResourceUtils.getFile(String.valueOf(child)).toPath();
                    log.debug("getting the file path from the file system", filePath);
                } catch (FileNotFoundException e) {
                    log.error("we can't get the file path from the file system", e);
                    throw new InvalidSourceException(String.format("Invalid source: %s", child));
                }

                try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filePath)))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        recipes.add(this.sourceDeserializer.deserialize(line));
                        log.info("adding each line of the recipe dataset to our list");
                    }
                } catch (IOException e) {
                    // We used a custom exception here to personalize our message and extend more methods if we need to scale,
                    //      our exception to handle more methods, as a result we don't need to log.
                    throw new InvalidSourceException(String.format("Unable to read recipe from file: %s", filePath));
                } catch (RecipeDeserializationException e) {
                    throw new InvalidSourceException(String.format("Unable to process recipe from file: %s", filePath));
                }

//                String recipeString = String.valueOf(filePath);
//                try {
//                    this.sourceDeserializer.deserialize(recipeString);
//                } catch (RecipeDeserializationException e) {
//                    throw new InvalidSourceException(String.format("Failed to process recipe: %s", recipeString));
//                }
            }
        }

        return recipes;
    };
}
