package me.jsj.item3.serialization;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter
public class Book implements Serializable {

    private static final long serialVersionUID = -1259111942252901808L;
    //static 또한 직렬화 대상에서 제외된다.
    public static String name;

    private String isbn;

    private String title;

    private String author;

    private LocalDate published;

    //transient를 통해 직렬화 대상에서 제외할 수 있다.
    private transient int numberOfSold;

    public Book(String isbn, String title, String author, LocalDate published) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published = published;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", published=" + published +
                ", numberOfSold=" + numberOfSold +
                ", name=" + Book.name +
                '}';
    }
}
