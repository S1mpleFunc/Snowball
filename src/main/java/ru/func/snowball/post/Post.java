package ru.func.snowball.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.func.snowball.post.content.Content;
import ru.func.snowball.post.content.Tag;

import java.util.List;

/**
 * @author func 10.12.2019
 */
@AllArgsConstructor
@Data
public class Post {

    private String url;
    private String author;
    private String timestamp;
    private List<Tag> tags;
    private List<Content> content;
}
