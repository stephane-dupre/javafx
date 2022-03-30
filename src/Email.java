import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.Date;

public class Email {
    private String subject;
    private String sender;
    private String body;
    private Date date;

    public Email(Message message) throws Exception {
        this.subject = message.getSubject();
        this.sender = ((InternetAddress)message.getFrom()[0]).getAddress();
        this.body = ReadEmails.getTextFromMessage(message);
        this.date = message.getSentDate();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
