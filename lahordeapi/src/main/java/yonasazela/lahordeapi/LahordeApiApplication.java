package yonasazela.lahordeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Lahorde API.
 */
@SpringBootApplication
public class LahordeApiApplication {

    private LahordeApiApplication() {
    }

    /**
     * Main entry point for the Spring Boot application.
     *
     * @param args command line arguments.
     */
    static void main(String[] args) {
		SpringApplication.run(LahordeApiApplication.class, args);
	}

    /**
     * Factory method to create a new instance of LahordeApiApplication.
     *
     * @return a new LahordeApiApplication instance.
     */
    public static LahordeApiApplication newLahordeApiApplication() {
        return new LahordeApiApplication();
    }
}
