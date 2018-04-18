package ${basePackage}.admin;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "${basePackage}.admin.service.impl")
public class ${adminApiModuleApplicatioName} {

	public static void main(String[] args) {
		SpringApplication.run(${adminApiModuleApplicatioName}.class, args);
	}
}
