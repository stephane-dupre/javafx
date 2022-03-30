import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EmailManager {
    protected final String username = "ynovsd@gmail.com";
    private final String password = "spkhiggetswqrtyx";
    private final Session session;
    private Store store;

    public EmailManager() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.imap.host", "imap.gmail.com");
        prop.put("mail.imap.port", "993");
        prop.put("mail.imap.starttls.enable", "true");

        this.session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
        );
    }

    public void connect() throws Exception {
        if (this.store == null) {
            this.store = this.session.getStore("imaps");
        }
        store.connect("imap.gmail.com", this.username, this.password);
    }

    public void close() {
        if(this.store == null) return;
        try {
            this.store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String dest, String subject, String text) {
        try {
            Message message = new MimeMessage(this.session);
            message.setFrom(new InternetAddress(this.username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public List<Message> readEmails() {
        List<Message> messages = new ArrayList<>();

        try {
            this.connect();

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            messages.addAll(Arrays.asList(emailFolder.getMessages()));

            for (Message message : messages) {
                Email email = new Email(message);

                System.out.println(email.getSubject());
                System.out.println(email.getSender());
                System.out.println(email.getDate());
                System.out.println(email.getBody());
                System.out.println("----------------------------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public static void main(String[] args) {
        EmailManager emailManager = new EmailManager();
        // emailManager.sendEmail("stephaned2205@gmail.com", "Test", "This is a test");
        emailManager.readEmails();
        emailManager.close();
    }
}
