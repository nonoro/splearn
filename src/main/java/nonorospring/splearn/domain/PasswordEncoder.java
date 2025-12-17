package nonorospring.splearn.domain;

import org.springframework.stereotype.Component;

@Component
public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
