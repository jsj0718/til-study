package me.jsj.demospringsecurity.book;

import lombok.Getter;
import lombok.Setter;
import me.jsj.demospringsecurity.account.Account;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Account author;
}
