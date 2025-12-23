package nonorospring.splearn.application.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nonorospring.splearn.application.member.provided.MemberFinder;
import nonorospring.splearn.application.member.provided.MemberRegister;
import nonorospring.splearn.application.member.required.EmailSender;
import nonorospring.splearn.application.member.required.MemberRepository;
import nonorospring.splearn.domain.member.*;
import nonorospring.splearn.domain.shared.Email;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Validated
@Transactional
@Service
public class MemberModifyService implements MemberRegister {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.deactivated();

        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUPdateRequest) {
        Member member = memberFinder.find(memberId);

        checkDuplicateProfile(member, memberInfoUPdateRequest.profileAddress());

        member.updateInfo(memberInfoUPdateRequest);

        return memberRepository.save(member);
    }

    private void checkDuplicateProfile(Member member, String profileAddress) {
        if (profileAddress.isEmpty()) return;

        Profile currentProfile = member.getDetail().getProfile();
        if (currentProfile != null && member.getDetail().getProfile().address().equals(profileAddress)) return;

        if (memberRepository.findByProfile(new Profile(profileAddress)).isPresent()) {
            throw new DuplicateProfileException("이미 존재하는 프로필 주소입니다" + profileAddress);
        }
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
        }
    }
}
