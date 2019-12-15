package ru.func.snowball.post.parser;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.func.snowball.post.Post;
import ru.func.snowball.post.parser.web.Recipient;

import java.io.IOException;

/**
 * @author func 10.12.2019
 */
@AllArgsConstructor
public class PostParser {

    private Recipient recipient;

    public Post getPost(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        return new Post(
                url,
                recipient.getAuthor(document),
                recipient.getTimestamp(document),
                recipient.getTags(document),
                recipient.getContent(document)
        );
    }
}
