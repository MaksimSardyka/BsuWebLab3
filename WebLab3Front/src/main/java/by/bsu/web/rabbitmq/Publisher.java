package by.bsu.web.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Publisher {
	private static Channel channel;

	static {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setUri("amqp://guest:guest@localhost");
			factory.setConnectionTimeout(300000);
			Connection connection = factory.newConnection();
			channel = connection.createChannel();

			// message-queue = name of the queue
			channel.queueDeclare("message-queue", true, false, false, null);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	public void sendMessage(String message) throws IOException {
		channel.basicPublish("", "message-queue", null, message.getBytes());
		log.debug("Message published: " + message);
	}
}