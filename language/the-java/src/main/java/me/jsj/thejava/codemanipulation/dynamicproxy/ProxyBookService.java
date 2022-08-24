package me.jsj.thejava.codemanipulation.dynamicproxy;

public class ProxyBookService implements BookService {

    BookService defaultBookService = new DefaultBookService();

    @Override
    public void rent(Book book) {
        System.out.println(">>>>>> 로그 a");
        defaultBookService.rent(book);
        System.out.println(">>>>>> 로그 b");
    }

    @Override
    public void returnBookTitle(Book book) {
        defaultBookService.returnBookTitle(book);
    }
}
