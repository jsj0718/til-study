package me.jsj.thejava.tests.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jsj.thejava.tests.study.StudyStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "STUDY")
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;
    private StudyStatus status;

    private String name;

    @Column(name = "number_of_people")
    private int limit;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member owner;

    private LocalDateTime openedDateTime;

    public Study() {
        this.status = StudyStatus.DRAFT;
    }

    public Study(int limit) {
        this();
        if (limit <= 0) throw new IllegalArgumentException("스터디의 최대 인원은 0명 이상이여야 합니다.");
        this.limit = limit;
    }

    public Study(String name, int limit) {
        this(limit);
        this.name = name;
    }

    public void open() {
        this.status = StudyStatus.OPENED;
        this.openedDateTime = LocalDateTime.now();
    }
}
