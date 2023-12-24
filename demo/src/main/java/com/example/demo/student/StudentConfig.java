package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.AUGUST;
import static java.time.Month.JUNE;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository
    ){
        return args -> {
            Student marta = new Student(
                    1L,
                    "Marta",
                    "marta.les44@gmail.com",
                    LocalDate.of(2003, AUGUST, 23)
            );

            Student sims = new Student(
                    "Sims",
                    "Sims4t@gmail.com",
                    LocalDate.of(1977, JUNE, 8)
            );

            repository.saveAll(
                    List.of(marta, sims)
            );
        };
    }
}
