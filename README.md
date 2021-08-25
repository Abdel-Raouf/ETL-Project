## Inside the `docker-compose.yml`:
    -  We can pass the path of the directory that contaning our dataset files  AS `command: ["scheduler 'your-directory-path'"]` (this command acts as the command line interface as it's a docker command that will path the parameter to the command line inside docker)
        - Ubuntu path example: `/home/your_username/input_folder/`
        - Windows path example: `d:/input_folder`
    - We can pass the recipeName that we need to execute only AS `command: ["executor recipe-name"]` or we could pass nothing, then it will execute all the results.

## Instructions to run the project (you must be in the same directory of the project to execute the below commands):

1. ./mvnw clean package -DskipTests  (to clean and build the project to the target folder)
2. cp target/etl-0.0.1-SNAPSHOT.jar src/main/docker (copy the created jar to docker main folder to read from it in our docker container)
3. sudo docker-compose up (to start all the containers)



## Project Architecture:
    - I Applied SOLID principles, decoupled each functionality and also decoupled technologies whenever is possible as using slf4j (logger interface -> Facade design pattern) on top of log4j to apply an abstraction layer between the app and the logger we will use to be able to replace the logger in the future without affecting our implementation of the logging which will still be the same for any logger we will use, as a result we can unit test with ease and might also don't need any integration tests between the components due to the granularity of each function, while expose them as a service which we might inject or replace it with a new implmentation without affecting the over-all design of the application (the system as a whole is open for any extenstions, closed for modifications), as a result we can scale easily.

## Technologies used:
    - `SpringBoot`: from my view, it helped me to focus more on the Architecture as it handles implementation of many used services pretty well.
    - `Apache Kafka`: an open-source distributed event streaming (will help us a lot when scalling enough to handle stream).
    - `Docker`: easily pack, ship, and run any application code base.
    - `Hibernate`: ORM 
    - `Postgresql`: relational database that is powerful enough and flexiable to our demand also, as we can convert it to be a column oriented DB using this extension -> `cstore_fdw`
    - `lombok`: manage boilerplate code as generating construcor, getter, setter, building objects.
    - `Jackson`: Transcoder that manages Serialization and Deserialization pretty well.
    - `Slf4j`: logger interface that can set on top of any logger
    - `log4j`: logger to manage warning, error, debug, info inside our code base.

## More elaboration on the project: (scaling our solution.)
    - using `AWS ECR` to hold our containers.
    - using `Apache Airflow` to create a pipeline to extract, transform, and load JSON data from `Amazon S3` to `Amazon Redshift`(Data Warehouse) (Automating the process of ETL) .
    - using `AWS lambda`  as our serverless computing service.
    - we can also expose the `KafkaExecuteService` as a web service using `AWS API Gateway` to accept the filter that we will pass to it the recipe name.



Note: when building the project it doesn't build successfuly due to there is a problem that take me a lot of time without any progression, while trying many different solutions AS this question on stackoverflow: `https://stackoverflow.com/questions/42907553/field-required-a-bean-of-type-that-could-not-be-found-error-spring-restful-ap`

Problem error:
    `Field schedulerService in com.ripple.etl.EtlApplication required a bean of type 'com.ripple.etl.SchedulerService' that could not be found.`

Note: I didn't implement unit testing, but if you want to see simple work, please refer to -> `https://github.com/Abdel-Raouf/Simple_Search_Engine/tree/initial-version/src/test/java`