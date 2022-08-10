package me.jsj.item7.cache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    private Integer id;

    private String title;

    private String content;

    @Override
    public void finalize() {
        System.out.println("gc called");
    }
}
