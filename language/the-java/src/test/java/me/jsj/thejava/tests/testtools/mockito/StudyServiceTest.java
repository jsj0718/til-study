package me.jsj.thejava.tests.testtools.mockito;

import me.jsj.thejava.tests.domain.Member;
import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.member.MemberService;
import me.jsj.thejava.tests.study.StudyRepository;
import me.jsj.thejava.tests.study.StudyService;
import me.jsj.thejava.tests.study.StudyStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyServiceV1() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        assertThat(new StudyService(memberService, studyRepository)).isNotNull();
    }

    @Test
    void createStudyServiceV2() {
        assertThat(new StudyService(memberService, studyRepository)).isNotNull();
    }

    @Test
    void createStudyServiceV3(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        assertThat(new StudyService(memberService, studyRepository)).isNotNull();
    }

    @Test
    void memberServiceMockTest() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member(1L, "test@test.com");

        //when & then (1)
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.findById(any())).thenReturn(Optional.of(member)); //ArgumentMatchers 사용

        assertThat(memberService.findById(1L)).isNotEmpty();
        assertThat(memberService.findById(1L).get().getEmail()).isEqualTo("test@test.com");
        assertThat(memberService.findById(2L)).isNotEmpty();
        assertThat(memberService.findById(2L).get().getEmail()).isEqualTo("test@test.com");

        //when & then (2)
        when(memberService.findById(1L)).thenThrow(IllegalArgumentException.class);
        assertThatThrownBy(() -> memberService.findById(1L)).isInstanceOf(IllegalArgumentException.class);

        //when & then (3)
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(RuntimeException.class)
                .thenReturn(Optional.empty());

        assertThat(memberService.findById(1L).get().getEmail()).isEqualTo("test@test.com");
        assertThatThrownBy(() -> memberService.findById(2L)).isInstanceOf(RuntimeException.class);
        assertThat(memberService.findById(3L)).isEmpty();
    }

    @Test
    void createStudyService() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertThat(studyService).isNotNull();

        Member member = new Member(1L, "test@test.com");
        Study study = new Study("테스트", 10);

        //when
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        //then
        assertThat(study.getOwner()).isNotNull();
        assertThat(study.getOwner()).isEqualTo(member);

        //verify
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        //InOrder (순서 검증)
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }

    @Test
    void bddTest() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertThat(studyService).isNotNull();

        Member member = new Member(1L, "test@test.com");
        Study study = new Study("테스트", 10);

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertThat(study.getOwner()).isEqualTo(member);
        then(memberService).should().notify(study);
        then(memberService).should().notify(member);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @Test
    void openTest() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study("더 자바, 테스트",10);
        assertThat(study.getOpenedDateTime()).isNull();
        given(studyRepository.save(study)).willReturn(study);

        //when
        studyService.openStudy(study);

        //then
        assertThat(study.getStatus()).isEqualTo(StudyStatus.OPENED);
        assertThat(study.getOpenedDateTime()).isNotNull();
        then(memberService).should().notify(study);
    }
}
