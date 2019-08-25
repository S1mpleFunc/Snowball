package ru.func.snowball.post.content;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContentImpl implements Content {

    private String tag;
    private String text;
    private String attribute;

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getContent() {
        return text;
    }

    @Override
    public String getAttribute() {
        return attribute;
    }
}
