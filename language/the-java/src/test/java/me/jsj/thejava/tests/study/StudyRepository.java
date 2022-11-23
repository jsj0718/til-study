package me.jsj.thejava.tests.study;

import me.jsj.thejava.tests.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findByName(String name);
}
