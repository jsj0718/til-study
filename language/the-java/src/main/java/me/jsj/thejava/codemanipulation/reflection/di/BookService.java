package me.jsj.thejava.codemanipulation.reflection.di;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
}
