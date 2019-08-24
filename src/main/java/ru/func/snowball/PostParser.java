package ru.func.snowball;

import org.jsoup.nodes.Document;

public interface PostParser {

    void getPost(Document document, String url, String dir);

}
