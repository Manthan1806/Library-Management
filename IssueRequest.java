import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class IssueRequest {
	public IssueRequest(Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String s = "create table if not exists issue_request(id varchar(15),name varchar(20),book varchar(20))";
		stmt.executeUpdate(s);
	}
	
	public void sendEmail(String b)
	{
		String s="sidhdarth37@gmail.com";
		
	      String to = s;//change accordingly
	      String from = "pednekar.manthan18@gmail.com";//change accordingly
	      String host = "localhost";//or IP address

	     //Get the session object
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);

	     //compose the message
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	         message.setSubject("Book Availablity");
	         message.setText("The book you have requested is available now");
	         
	         // Send message
	         Transport.send(message);
	         System.out.println("message sent successfully....");

	      }catch (MessagingException mex) {mex.printStackTrace();}

	}
}
