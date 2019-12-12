package by.bsu.web.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SentSmtpEmailFunctionHandler implements RequestHandler<String, Object> {

	// Replace sender@example.com with your "From" address.
	// This address must be verified.
	static final String FROM = "maximaddress@gmail.com";
	static final String FROMNAME = "Maksim";

	// Replace recipient@example.com with a "To" address. If your account
	// is still in the sandbox, this address must be verified.
	static final String TO = "maximaddress@gmail.com";

	// Replace smtp_username with your Amazon SES SMTP user name.
	static final String SMTP_USERNAME = "AKLATJCJZWLSUHPPFUFC";

	// Replace smtp_password with your Amazon SES SMTP password.
	static final String SMTP_PASSWORD = "BISq49HvYsnlJfJoiT4mJTh2M8JKkYQJMokvqV!cga7b";

	// The name of the Configuration Set to use for this message.
	// If you comment out or remove this variable, you will also need to
	// comment out or remove the header below.
	// static final String CONFIGSET = "ConfigSet";

	// Amazon SES SMTP host name. This example uses the US West (Oregon) region.
	// See
	// https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
	// for more information.
	static final String HOST = "email-smtp.us-west-2.amazonaws.com";

	// The port you will connect to on the Amazon SES SMTP endpoint.
	static final int PORT = 587;

	static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

	@Override
	public Object handleRequest(String input, Context context) {

		// Create a Properties object to contain connection configuration information.
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		// Create a Session object to represent a mail session with the specified
		// properties.
		Session session = Session.getDefaultInstance(props);

		// Create a message with the specified information.
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setSubject(SUBJECT);
			msg.setContent(input, "text/html");
			// Add a configuration set header. Comment or delete the
			// next line if you are not using a configuration set
			// msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Send the message.
		try (Transport transport = session.getTransport();) {
			System.out.println("Sending...");

			// Connect to Amazon SES using the SMTP username and password you specified
			// above.
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

			// Send the email.
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		}
		return "Send";
	}

}
