package me.jsj.thejava.codemanipulation.dynamicproxy;

public class DefaultBookService implements BookService {

    public void rent(Book book) {
        System.out.println("rent = " + book.getTitle());
    }

    @Override
    public void returnBookTitle(Book book) {
        System.out.println("returnBookTitle = " + book.getTitle());
    }

}
