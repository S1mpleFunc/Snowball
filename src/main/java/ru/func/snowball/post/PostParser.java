package ru.func.snowball.post;

import org.jsoup.nodes.Document;

public interface PostParser {

    void getPost(Document document, String url, String dir);

}
