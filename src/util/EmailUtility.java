package util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailUtility {
    public static void sendEmail(String emailSubject,String emailText){
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("philipjf.hw@gmail.com", "Example.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("philipjf.hw+ps5availabilitychecker@gmail.com", "Mr. User"));
            msg.setSubject(emailSubject);
            msg.setText(emailText);
            Transport.send(msg);
        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        } catch (UnsupportedEncodingException e) {
            // ...
        }
    }
}
