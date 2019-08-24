package ru.func.snowball.post.content.element;

import lombok.AllArgsConstructor;
import ru.func.snowball.post.content.Content;

@AllArgsConstructor
public class Text implements Content {

    private String text;

    @Override
    public String getContent() {
        return text;
    }
}
