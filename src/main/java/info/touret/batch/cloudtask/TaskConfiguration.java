package info.touret.batch.cloudtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Configuration
@EnableTask
public class TaskConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskConfiguration.class);

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner createCommandLineRunner(RestTemplate restTemplate) {
        return args -> {
            var commandLinePropertySource = new SimpleCommandLinePropertySource(args);
            var entity = restTemplate.getForEntity("https://api.nationalize.io/?name=" + Optional.ofNullable(commandLinePropertySource.getProperty("name")).orElse("BLANK"), NationalizeResponseDTO.class);
            LOGGER.info("RESPONSE[{}]: {}", entity.getStatusCode(), entity.getBody());
        };
    }
}
