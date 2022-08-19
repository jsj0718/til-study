package me.jsj.practice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, Long> {

    List<Domain> findByName(String name);

    @Query("select d from Domain d join d.joinDomains jd where jd.id = :id")
    Domain findNameLive(Long id);

    @Query("select jd from Domain d join d.joinDomains jd")
    List<JoinDomain> findAllJoinDomain();

}
