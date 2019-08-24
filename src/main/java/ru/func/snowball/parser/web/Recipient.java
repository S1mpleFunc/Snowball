package ru.func.snowball.parser.web;

import org.jsoup.nodes.Document;
import ru.func.snowball.content.Content;
import ru.func.snowball.content.Tag;

import java.util.List;

public interface Recipient {

    String getAuthor(Document document);
    String getTimestamp(Document document);
    List<Tag> getTags(Document document);
    List<Content> getContent(Document document);

}
