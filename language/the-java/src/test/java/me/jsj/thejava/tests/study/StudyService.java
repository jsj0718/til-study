package me.jsj.thejava.tests.study;

import lombok.RequiredArgsConstructor;
import me.jsj.thejava.tests.domain.Member;
import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.member.MemberNotFoundException;
import me.jsj.thejava.tests.member.MemberService;

import java.util.Optional;

@RequiredArgsConstructor
public class StudyService {

    private final MemberService memberService;

    private final StudyRepository studyRepository;

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new MemberNotFoundException()));
        Study savedStudy = studyRepository.save(study);
        memberService.notify(savedStudy);
        memberService.notify(member.get());
        return savedStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = studyRepository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }
}
