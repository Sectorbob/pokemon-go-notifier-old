package sectorbob.gaming.pokemon.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sectorbob.gaming.pokemon.model.Pokemon;
import sectorbob.gaming.pokemon.model.Subscriber;
import sectorbob.gaming.pokemon.util.Util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by ltm688 on 7/26/16.
 */
public class EmailClient {

    private static Logger LOG = LoggerFactory.getLogger(EmailClient.class);

    private String fromEmail;
    private String username;
    private String password;
    private Properties props;

    Session session;
    Transport transport;

    public EmailClient(String user, final String password) throws MessagingException {
        this.fromEmail = user + "@gmail.com";
        this.username = user;
        this.password = password;

        props = System.getProperties();
        //props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", fromEmail);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        session = Session.getDefaultInstance(props, auth);

        transport = session.getTransport("smtp");

        transport.connect("smtp.gmail.com", username, password);
    }

    public void send(Pokemon pokemon, Subscriber subscriber) {
        String message = pokemon.getName() + " spotted. expires at " +
                Util.getExpiryTime(pokemon.getExpiryMillis()) + " near " + pokemon.getGeneralLocation() + " " + Util.generateGoogleMapsLink(pokemon);

        String email =  getEmailForContact(subscriber.getContact(), subscriber.getCarrier());

        LOG.info("Notifying " + email + " about " + pokemon);

        send("", message, email);
    }

    public void send(String subject, String messageBody, String recipient) {

        MimeMessage message = new MimeMessage(session);

        // Create the email addresses involved
        try {
            InternetAddress from = new InternetAddress(fromEmail);

            message.setSubject(subject);
            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageBody);

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            String htmlMessage = "Our html text";
            messageBodyPart.setContent(htmlMessage, "text/html");

            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            transport.sendMessage(message, message.getAllRecipients());


        } catch (AddressException e) {
            LOG.error("An error occurred sending an email to address " + recipient, e);
        } catch (MessagingException e) {
            LOG.error("Unable to send email to " + recipient);
        }
    }


    public static String getEmailForContact(String contact, String provider) {
        switch(provider) {
            case "T_MOBILE":
                return contact + "@tmomail.net";
            case "ATT":
                return contact + "@txt.att.net";
            case "VERIZON":
                return contact + "@vtext.com";
            case "SPRINT":
                return contact + "@messaging.sprintpcs.com";
            case "CRICKET":
                return contact + "@sms.mycricket.com";
            default:
                LOG.info("Contact ("+contact+") has no carrier. Attempting to do plain email..");
                return contact;
        }
    }
}