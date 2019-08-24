package ru.func.snowball.post.parser.web;

import org.jsoup.nodes.Document;
import ru.func.snowball.post.content.Content;
import ru.func.snowball.post.content.element.Image;
import ru.func.snowball.post.content.Tag;
import ru.func.snowball.post.content.element.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecipientPikabu implements Recipient {

    @Override
    public String getAuthor(Document document) {
        return document.getElementsByAttribute("data-name").get(0).attr("data-name");
    }

    @Override
    public String getTimestamp(Document document) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long time = 0;

        try {
            time = format.parse(document.getElementsByAttribute("datetime").get(0).attr("datetime")).getTime();
        } catch (ParseException ignored) {
        }

        return time + "";
    }

    @Override
    public List<Tag> getTags(Document document) {

        List<Tag> tags = new ArrayList<>();

        for (org.jsoup.nodes.Element element : document.getElementsByClass("tags__tag"))
            if (element.hasAttr("data-tag"))
                tags.add(new Tag(element.attr("data-tag")));

        return tags;
    }

    @Override
    public List<Content> getContent(Document document) {

        List<Content> contents = new ArrayList<>();

        for (org.jsoup.nodes.Element element : document.getElementsByClass("story__content-inner").get(0).children()) {
            if (element.children().size() > 0)
                for (org.jsoup.nodes.Element child : element.children())
                    if (child.hasText())
                        contents.add(new Text(child.text()));
            if (element.getElementsByAttribute("href").size() > 0)
                contents.add(new Image(element.getElementsByAttribute("href").get(0).attr("href")));
        }

        return contents;
    }
}
