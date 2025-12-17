package nonorospring.splearn.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nonorospring.splearn.application.provided.MemberFinder;
import nonorospring.splearn.application.required.MemberRepository;
import nonorospring.splearn.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Validated
@Transactional
@Service
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    }
}
