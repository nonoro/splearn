package nonorospring.splearn.application;

import lombok.RequiredArgsConstructor;
import nonorospring.splearn.application.provided.MemberRegister;
import nonorospring.splearn.application.required.EmailSender;
import nonorospring.splearn.application.required.MemberRepository;
import nonorospring.splearn.domain.Member;
import nonorospring.splearn.domain.MemberRegisterRequest;
import nonorospring.splearn.domain.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");

        return member;
    }
}
