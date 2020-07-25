package covid.tracing.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JavaMailSenderImpl mailSender;

    @Autowired
    public EmailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String authKey, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ejsvk3284@gmail.com");
        message.setTo(email);
        message.setSubject("[Covid-Tracing] 인증 번호 발송");
        message.setText(authKey);
        mailSender.send(message);
    }
}
