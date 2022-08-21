package me.jsj.practice;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Domain {

    @Id @GeneratedValue
    @Column(name = "DOMAIN_ID")
    private Long id;

    private String name;

    private boolean flag;

    @OneToMany(mappedBy = "domain")
    private List<JoinDomain> joinDomains = new ArrayList<>();

    @Builder
    public Domain(Long id, String name, boolean flag, List<JoinDomain> joinDomains) {
        this.id = id;
        this.name = name;
        this.flag = flag;
        this.joinDomains = joinDomains;
    }
}
