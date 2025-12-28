package nonorospring.splearn.adapter.webapi;

import lombok.RequiredArgsConstructor;
import nonorospring.splearn.application.member.provided.MemberRegister;
import nonorospring.splearn.domain.member.Member;
import nonorospring.splearn.domain.member.MemberFixture;
import nonorospring.splearn.domain.member.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@WebMvcTest(MemberApi.class)
class MemberApiWebMvcTest {
    @MockitoBean
    MemberRegister memberRegister;

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @Test
    void register() {
        Member member = MemberFixture.createMember(1L);
        when(memberRegister.register(any())).thenReturn(member);

        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.memberId").asNumber().isEqualTo(1);

        verify(memberRegister).register(request);
    }

    @Test
    void registerFail() {
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest("invalid-email");
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }
}
