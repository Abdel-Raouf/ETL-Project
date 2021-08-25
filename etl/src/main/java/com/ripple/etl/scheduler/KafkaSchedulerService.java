package com.ripple.etl.scheduler;

import com.ripple.etl.SchedulerService;
import com.ripple.etl.common.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
//Note:@Component serves as a generic stereotype for any Spring-managed component; whereas, @Repository, @Service, and @Controller
// serve as specializations of @Component for more specific use cases, your classes are more properly suited for processing by tools
// or associating with aspects.For example, these stereotype annotations make ideal targets for pointcuts (AOP).
public class KafkaSchedulerService implements SchedulerService{
    private final SchedulerReader schedulerReader;
    private final List<RecipeTransformer> schedulerTransformers;
    private final SchedulerWriter schedulerWriter;

    // Constructor injection allows us to test the class in a unit test without code that depends on Spring.
    //  as a result we don't even need to mock when we testing as it's done automatically for us.
    @Autowired
    public KafkaSchedulerService(
            SchedulerReader schedulerReader,
            @Qualifier("RecipeTransformersList") List<RecipeTransformer> schedulerTransformers,
            SchedulerWriter schedulerWriter
    ) {
        this.schedulerReader = schedulerReader;
        this.schedulerTransformers = schedulerTransformers;
        this.schedulerWriter = schedulerWriter;
    }

    /**
     *
     * @param source Directory path we get from the docker-compose.yml
     * @throws InvalidSourceException exception to handle any problem arise when processing the source file path.
     */

    @Override
    public void schedule(String source) throws InvalidSourceException {
        log.info("SchedulerService collect our functionalities");

        List<Recipe> recipes = this.schedulerReader.read(source);
        log.info("read our file from the file path we get as an argument.");
        for (Recipe recipe : recipes) {
            for (RecipeTransformer transformer : this.schedulerTransformers) {
                log.info("We pass our list of transformers to transform our recipes");
                recipe = transformer.transform(recipe);
            }
            log.info("write our recipe to kafka");
            this.schedulerWriter.write(recipe);
        }
    }
}

