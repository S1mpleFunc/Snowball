package ru.func.snowball.post.content.element;

import lombok.AllArgsConstructor;
import ru.func.snowball.post.content.Content;

@AllArgsConstructor
public class Image implements Content {

    private String image;

    @Override
    public String getContent() {
        return image;
    }
}
