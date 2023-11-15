package ite.jp.ak.lab03.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ite.jp.ak.lab03")
@EntityScan(basePackages = "ite.jp.ak.lab03")
@EnableJpaRepositories(basePackages = "ite.jp.ak.lab03")
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
