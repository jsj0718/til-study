package me.jsj.thejava.tests.member;

import me.jsj.thejava.tests.domain.Member;
import me.jsj.thejava.tests.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
