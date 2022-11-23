package me.jsj.thejava.tests;

import lombok.RequiredArgsConstructor;
import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.study.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitApplication implements ApplicationRunner {

    @Autowired
    StudyRepository studyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        studyRepository.save(new Study("Java", 10));
    }
}
