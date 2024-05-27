package com.youx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

 
@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
public class TextePraticoYouxApplication implements WebMvcConfigurer  {

	public static void main(String[] args) {
		SpringApplication.run(TextePraticoYouxApplication.class, args);
		
		   //System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		   
		                     registry.addMapping("/clientes/**")
		                     .allowedMethods("*")
		                     .allowedOrigins("*");
		                     
		                     registry.addMapping("/estados/**")
		                     .allowedMethods("*")
		                     .allowedOrigins("*");
	}

}
