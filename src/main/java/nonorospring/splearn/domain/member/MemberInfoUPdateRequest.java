package nonorospring.splearn.domain.member;

public record MemberInfoUPdateRequest(
        String nickname,
        String profileAddress,
        String introduction
) {
}
