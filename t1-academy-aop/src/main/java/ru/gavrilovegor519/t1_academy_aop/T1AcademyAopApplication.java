package ru.gavrilovegor519.t1_academy_aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class T1AcademyAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(T1AcademyAopApplication.class, args);
	}

}
