package ru.func.snowball.content;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Image implements Content {

    private String image;

    @Override
    public String getContent() {
        return image;
    }
}
