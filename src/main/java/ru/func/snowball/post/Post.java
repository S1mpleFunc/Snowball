package ru.func.snowball.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.func.snowball.post.content.Content;
import ru.func.snowball.post.content.Tag;

import java.util.List;

@AllArgsConstructor
public class Post {

    @Getter
    private String url;
    private String author;
    private String timestamp;
    private List<Tag> tags;
    private List<Content> content;
}
