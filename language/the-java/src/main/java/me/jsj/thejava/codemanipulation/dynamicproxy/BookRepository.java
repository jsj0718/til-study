package me.jsj.thejava.codemanipulation.dynamicproxy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
