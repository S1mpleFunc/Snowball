package ru.func.snowball.parser.web;

import org.jsoup.nodes.Document;
import ru.func.snowball.content.*;

import java.util.ArrayList;
import java.util.List;

public class RecipientJoyReactor implements Recipient {

    @Override
    public String getAuthor(Document document) {
        return document.getElementsByClass("avatar").get(0).attr("alt");
    }

    @Override
    public String getTimestamp(Document document) {
        return document.getElementsByAttribute("data-time").get(0).attr("data-time");
    }

    @Override
    public List<Tag> getTags(Document document) {

        List<Tag> tags = new ArrayList<>();

        for (org.jsoup.nodes.Element element : document.getElementsByClass("taglist").get(0).children())
            if (element.children().size() > 0 && !element.children().get(0).attr("title").isEmpty())
                tags.add(new Tag(element.children().get(0).attr("title")));

        return tags;
    }

    @Override
    public List<Content> getContent(Document document) {

        List<Content> contents = new ArrayList<>();

        for (org.jsoup.nodes.Element element : document.getElementsByClass("image").get(0).parent().children()) {
            if (element.hasText())
                contents.add(new Text(element.text()));
            else if (element.hasClass("image")) {
                org.jsoup.nodes.Element child = element.getElementsByAttribute("src").get(0);
                contents.add(child.tagName().equals("img") ?
                        new Image(child.attr("src")) : child.tagName().equals("iframe") ?
                        new Video(child.attr("src"), child.className().split("-")[0]) : new Text(""));
            }
        }

        return contents;
    }
}
