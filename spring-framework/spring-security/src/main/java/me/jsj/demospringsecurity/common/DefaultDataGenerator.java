package me.jsj.demospringsecurity.common;

import lombok.RequiredArgsConstructor;
import me.jsj.demospringsecurity.account.Account;
import me.jsj.demospringsecurity.account.AccountService;
import me.jsj.demospringsecurity.book.Book;
import me.jsj.demospringsecurity.book.BookRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultDataGenerator implements ApplicationRunner {

    private final AccountService accountService;

    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //jsj - Spring
        Account jsj = createUser("jsj", "123", "USER");
        createBook(jsj, "Spring");

        //sejin - JPA
        Account sejin = createUser("sejin", "123", "USER");
        createBook(sejin, "JPA");
    }

    private void createBook(Account jsj, String title) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(jsj);
        bookRepository.save(book);
    }

    private Account createUser(String username, String password, String role) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(role);

        return accountService.createNewAccount(account);
    }
}
