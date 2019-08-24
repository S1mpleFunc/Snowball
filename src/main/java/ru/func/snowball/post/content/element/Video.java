package ru.func.snowball.post.content.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.func.snowball.post.content.Content;

@Getter
@AllArgsConstructor
public class Video implements Content {

    private String video;
    private String type;

    @Override
    public Video getContent() {
        return this;
    }
}
