package nonorospring.splearn.application.provided;

import jakarta.transaction.Transactional;
import nonorospring.splearn.SplearnTestConfiguration;
import nonorospring.splearn.domain.DuplicateEmailException;
import nonorospring.splearn.domain.Member;
import nonorospring.splearn.domain.MemberFixture;
import nonorospring.splearn.domain.MemberStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(SplearnTestConfiguration.class)
@Transactional
@SpringBootTest
public record MemberRegisterTest(MemberRegister memberRegister) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }
}
