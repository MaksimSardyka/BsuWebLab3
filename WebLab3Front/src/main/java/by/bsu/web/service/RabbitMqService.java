package by.bsu.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.bsu.web.rabbitmq.Publisher;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitMqService {
	@Autowired
	Publisher publisher;

	public void sendMessageToQueue(String message) {
		try {
			publisher.sendMessage(message);
		} catch (IOException e) {
			log.error(e.toString());
		}
	}
}
