package com.ripple.etl;

import com.ripple.etl.scheduler.InvalidSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class EtlApplication implements CommandLineRunner {
	public static final String SCHEDULE_COMMAND = "schedule";
	private static final String EXECUTE_COMMAND = "execute";

	@Autowired
	// using "dependency injection" we can define interfaces, and the injector will instantiate the objects
	// 	of required implementation which implement this interface. if the interfaces is implemented by more than one class, then
	// we need to annotate it with @primary, to tell DP with implementation be the primary one, then we can map the other not primary
	// implementations.
	private SchedulerService schedulerService;
	@Autowired // Autowired annotations is a Field-Based dependency injection.
	private ExecutorService executorService;
	@Autowired
	private AlertService alertService;

	public static void main(String[] args) {
		SpringApplication.run(EtlApplication.class, args);
	}

	/**
	 * Can be called from docker-compose.yml file using:
	 *   - command: schedule path/to/dataset
	 *   - command execute [optional task id]
 	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		String command = args[0];
		log.info(String.format("Received command: %s", command));
		log.debug(String.format("Received command line args: %s", args));
		// run either scheduler or executor based on the command line arguments
		if (command.equals(SCHEDULE_COMMAND)) {
			String source = args[1];
			try {
				this.schedulerService.schedule(source);
			} catch (InvalidSourceException e) {
				log.error("we can't read the directory path we get ", e);
				this.alertService.sendAlert(e);
			}
		} else if (command.equals(EXECUTE_COMMAND)) {
			Optional<String> taskName = Optional.ofNullable(args[1]);
			this.executorService.Execute(taskName);
		} else {
			throw new RuntimeException(String.format("Command not supported: '%s'", command));
		}
	}
}
