package com.wrotecode.filepost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FilePostApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FilePostApp.class, args);
        System.out.println(run);
    }
}
