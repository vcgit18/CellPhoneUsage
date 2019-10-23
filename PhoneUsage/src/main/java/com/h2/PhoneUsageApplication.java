package com.h2;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * @Author: Linus Kwong
 * @2019, All Rights Reserved
 * 
 * Description: Main program to generate cell phone usage report
 */
@SpringBootApplication
@Controller
public class PhoneUsageApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(PhoneUsageApplication.class);
    
	@Value("${welcome.message:test}")
	private String message = "Welcome to the show !";
	
	@Autowired
	private LoadInputData loadInData;
	
	public static void main(String[] args) {
		SpringApplication.run(PhoneUsageApplication.class, args);
	}
	
	@RequestMapping(value = "/")
	public String hello(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("OptionNames: {}", args.getOptionNames());
        for (String name : args.getOptionNames()){
            logger.info("argument (" + name + "):" + args.getOptionValues(name));
        }        
        loadInData.run();
	}
	
}
