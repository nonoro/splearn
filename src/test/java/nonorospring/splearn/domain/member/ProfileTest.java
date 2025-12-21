package nonorospring.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    @Test
    void profile() {
        new Profile("nonorokwon");
        new Profile("nonoro100");
        new Profile("31241412512");
    }

    @Test
    void profileFail() {
        Assertions.assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("nonorokwon");

        assertThat(profile.url()).isEqualTo("@nonorokwon");
    }
}
