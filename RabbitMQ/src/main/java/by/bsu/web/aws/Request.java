package by.bsu.web.aws;

import lombok.Data;

@Data
public class Request {
	private String httpMethod;
	//Message data
	private String message;
}
