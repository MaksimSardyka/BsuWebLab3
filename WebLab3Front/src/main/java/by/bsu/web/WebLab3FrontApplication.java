package by.bsu.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import by.bsu.web.rabbitmq.Consumer;

@SpringBootApplication
public class WebLab3FrontApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebLab3FrontApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebLab3FrontApplication.class, args);
	}

	@Bean
	public Consumer initConsumer() {
		// "message-queue" - name of the queue
		return new Consumer("amqp://guest:guest@localhost", "message-queue");
	}

	@Bean
	public Thread initRabbitMqConsumerDaemon(Consumer consumer) {
		Thread thread = new Thread(consumer);
		thread.setDaemon(true);
		thread.start();
		return thread;
	}
}