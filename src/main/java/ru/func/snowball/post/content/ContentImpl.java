package ru.func.snowball.post.content;

import lombok.AllArgsConstructor;

/**
 * @author func 10.12.2019
 */
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
