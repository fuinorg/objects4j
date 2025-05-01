package org.fuin.objects4j.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Represents the (custom) entry point, most likely used to the Quarkus application in the IDE.
 */
@SpringBootApplication
public class SpringBootApp {

    /**
     * Main method to start the app.
     *
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

}