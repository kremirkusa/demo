package com.example.demo.course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CourseConfig {
    @Bean
    CommandLineRunner courseCommandLineRunner(
            CourseRepository repository
    ){
        return args -> {
            Course java = new Course(
                    1L,
                    "Čupićeva Java",
                    "36"
            );

            Course vjekom = new Course(
                    "Vjekom",
                    "700"
            );

            repository.saveAll(
                    List.of(java, vjekom)
            );
        };
    }
}
