package me.jsj.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class JpaTest {

    @Autowired
    DomainRepository domainRepository;

    @Autowired
    JoinDomainRepository joinDomainRepository;

    @Test
    void testIsEmpty() {
        JoinDomain joinDomain1 = JoinDomain.builder().value("test1").build();
        JoinDomain joinDomain2 = JoinDomain.builder().value("test2").build();
        JoinDomain joinDomain3 = JoinDomain.builder().value("test3").flag(true).build();

        List<JoinDomain> joinDomains = new ArrayList<>();
        joinDomains.add(joinDomain1);
        joinDomains.add(joinDomain2);
        joinDomains.add(joinDomain3);

        Domain domain = Domain.builder().name("jsj").flag(false).joinDomains(joinDomains).build();
        joinDomain1.setDomain(domain);
        joinDomain2.setDomain(domain);
        joinDomain3.setDomain(domain);

        joinDomainRepository.save(joinDomain1);
        joinDomainRepository.save(joinDomain2);
        joinDomainRepository.save(joinDomain3);
        Domain saveDomain = domainRepository.save(domain);

        assertThat(joinDomain1.getId()).isEqualTo(1L);
        assertThat(joinDomain2.getId()).isEqualTo(2L);
        assertThat(joinDomain3.getId()).isEqualTo(3L);
        assertThat(saveDomain.getId()).isEqualTo(4L);

        assertThat(saveDomain.getJoinDomains().get(0).getId()).isEqualTo(1L);
        assertThat(saveDomain.getJoinDomains().get(1).getId()).isEqualTo(2L);
        assertThat(saveDomain.getJoinDomains().get(2).getId()).isEqualTo(3L);

        assertThat(saveDomain.getJoinDomains()).isNotNull();
        assertThat(saveDomain.getJoinDomains().get(0).getValue()).isEqualTo("test1");

        assertThat(domainRepository.findAllJoinDomain().size()).isEqualTo(3);
        assertThat(domainRepository.findNameLive(1L).getId()).isEqualTo(4L);

        assertThat(joinDomainRepository.findByDomainId(4L).size()).isEqualTo(1);
        assertThat(joinDomainRepository.findByDomainId(4L).get(0).getId()).isEqualTo(3L);
    }
}
