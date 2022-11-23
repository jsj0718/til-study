package me.jsj.thejava.tests.testtools.unit;

import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.study.StudyRepository;
import me.jsj.thejava.tests.study.StudyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudyRepsoitoryDataJpaTest {

    @Autowired
    StudyRepository studyRepository;

    @Test
    @DisplayName("스터디 생성 및 조회 - 성공")
    void findById() {
        //init
        studyRepository.deleteAll();

        //given
        Study study = new Study("Java", 10);

        //when
        studyRepository.save(study);

        //then
        Study findStudy = studyRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException());

        assertThat(findStudy.getName()).isEqualTo("Java");
        assertThat(findStudy.getLimit()).isEqualTo(10);
        assertThat(findStudy.getStatus()).isEqualTo(StudyStatus.DRAFT);
    }

    @Test
    @DisplayName("스터디 이름 조회 - 성공")
    void findByName() {
        //init
        studyRepository.deleteAll();

        //given
        Study study = new Study("Java", 10);

        //when
        studyRepository.save(study);

        //then
        List<Study> studyList = studyRepository.findByName("Java");

        assertThat(studyList.size()).isEqualTo(1);
        assertThat(studyList.get(0).getName()).isEqualTo("Java");
        assertThat(studyList.get(0).getLimit()).isEqualTo(10);
        assertThat(studyList.get(0).getStatus()).isEqualTo(StudyStatus.DRAFT);
    }
}
