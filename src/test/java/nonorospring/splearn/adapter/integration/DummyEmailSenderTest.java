package nonorospring.splearn.adapter.integration;

import nonorospring.splearn.domain.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {
    @StdIo
    @Test
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("nonoro@splearn.app"), "subject", "body");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender sending email: Email[address=nonoro@splearn.app]");
    }

}
