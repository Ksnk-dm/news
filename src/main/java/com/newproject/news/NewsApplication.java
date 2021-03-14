package com.newproject.news;

import com.newproject.news.controllers.NewsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class NewsApplication {

	public static void main(String[] args) {
		new File(NewsController.uploadDirectory).mkdir();
		SpringApplication.run(NewsApplication.class, args);
	}

}
