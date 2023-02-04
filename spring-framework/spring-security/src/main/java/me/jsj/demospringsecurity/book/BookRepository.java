package me.jsj.demospringsecurity.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    //spring-security-data에서 @Query 안에 principal 사용 가능하도록 지원
    @Query("select b from Book b where b.author.id = ?#{principal.account.id}")
    List<Book> findCurrentUserBooks();
}
