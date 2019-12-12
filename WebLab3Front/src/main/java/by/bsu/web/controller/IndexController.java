package by.bsu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.bsu.web.service.RabbitMqService;

@Controller
public class IndexController {
	@Autowired
	RabbitMqService rabbitMqService;

	@RequestMapping("/sendMessage")
	public String hello(Model model,
			@RequestParam(value = "message", required = false, defaultValue = "") String message) {
		model.addAttribute("message", message);
		rabbitMqService.sendMessageToQueue(message);
		return "index";
	}
}
