package me.jsj.item3.serialization;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    void serialize(Book book) {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("book.obj"))) {
            out.writeObject(book);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Book deserialized() {
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("book.obj"))) {
            return  (Book) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSerializable() {
        Book book = new Book("12345", "이펙티브 자바", "조슈아 블로크", LocalDate.of(2014, 9, 1));
        book.setNumberOfSold(200);
        Book.name = "정대만";

        serialize(book);
        Book deserializedBook = deserialized();
        Book.name = "정세진";

        System.out.println(book);
        System.out.println(deserializedBook);
    }
}