package com.example.rinha2024;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.Instant;

@SpringBootApplication
public class Rinha2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Rinha2024Application.class, args);
	}

	// registrar um servlet apenas
	@Bean
	public ServletRegistrationBean<?> bean() {
		return new ServletRegistrationBean<>(new HttpServlet() {
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			resp.setStatus(200);
			resp.getWriter().println(Instant.now());
			}
		}, "/health");
	}

}
