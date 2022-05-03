package com.example.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * do something when application started
 */
@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ApplicationStartupRunner.class);

    @Override
    public void run(String... args) throws Exception {

        logger.info("ApplicationStartupRunner started, args:{}", Arrays.toString(args));

    }
}
