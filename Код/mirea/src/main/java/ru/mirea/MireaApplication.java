package ru.mirea;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MireaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MireaApplication.class, args);
		Application.launch(Start.class);
	}

}