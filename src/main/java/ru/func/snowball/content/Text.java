package ru.func.snowball.content;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Text implements Content {

    private String text;

    @Override
    public String getContent() {
        return text;
    }
}
