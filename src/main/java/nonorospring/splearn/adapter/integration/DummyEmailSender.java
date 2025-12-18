package nonorospring.splearn.adapter.integration;

import nonorospring.splearn.application.required.EmailSender;
import nonorospring.splearn.domain.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Fallback
@Component
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender sending email: " + email);
    }
}
