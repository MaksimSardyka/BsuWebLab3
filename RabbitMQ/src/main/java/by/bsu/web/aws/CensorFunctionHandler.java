package by.bsu.web.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CensorFunctionHandler implements RequestHandler<Request, String> {

	@Override
	public String handleRequest(Request input, Context context) {
		String censoredString = input.getMessage().replaceAll("\\bBAD\\b", "*");
		return new StringBuilder().append("{ \"message\": \"")
				.append(censoredString).append("\"}").toString();
	}
}
