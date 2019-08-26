package ru.func.snowball.post;

import org.jsoup.nodes.Document;

public interface PostParser {

    String getPost(Document document, String url, String dir);

}
