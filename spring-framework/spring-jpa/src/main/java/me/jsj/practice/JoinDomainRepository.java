package me.jsj.practice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JoinDomainRepository extends JpaRepository<JoinDomain, Long> {

    @Query("select jd from JoinDomain jd where jd.domain.id = ?1 and jd.flag = true")
    List<JoinDomain> findByDomainId(Long id);
}
