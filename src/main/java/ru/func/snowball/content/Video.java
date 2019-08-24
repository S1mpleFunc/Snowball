package ru.func.snowball.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
