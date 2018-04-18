package ${basePackage};

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "${basePackage}.api.impl")
public class ${providerModuleApplicationName} {

	public static void main(String[] args) {
		SpringApplication.run(${providerModuleApplicationName}.class, args);
	}
}
