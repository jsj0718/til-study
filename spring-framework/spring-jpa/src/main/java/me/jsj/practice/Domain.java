package me.jsj.practice;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
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

}
