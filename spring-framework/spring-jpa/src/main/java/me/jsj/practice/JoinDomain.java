package me.jsj.practice;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class JoinDomain {

    @Id @GeneratedValue
    @Column(name = "JOIN_DOMAIN_ID")
    private Long id;

    private String value;

    private boolean flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID")
    private Domain domain;
}
