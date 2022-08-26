package me.jsj.thejava.codemanipulation.reflection.springdi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository bookRepository;
}
