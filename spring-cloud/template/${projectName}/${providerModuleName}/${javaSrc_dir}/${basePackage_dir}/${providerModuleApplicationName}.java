package ${basePackage};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableEurekaClient
@SpringBootApplication
public class ${providerModuleApplicationName} {

	public static void main(String[] args) {
		SpringApplication.run(${providerModuleApplicationName}.class, args);
	}
}
