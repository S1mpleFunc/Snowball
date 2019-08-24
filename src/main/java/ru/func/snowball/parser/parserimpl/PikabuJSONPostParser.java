package ru.func.snowball.parser.parserimpl;

import ru.func.snowball.Post;
import ru.func.snowball.parser.JSONPostParser;
import ru.func.snowball.parser.web.Recipient;
import ru.func.snowball.parser.web.RecipientPikabu;

public class PikabuJSONPostParser implements JSONPostParser {

    private Recipient pikabu = new RecipientPikabu();

    @Override
    public void getPost(org.jsoup.nodes.Document document, String url, String dir) {
        Post post = new Post(
                url,
                pikabu.getAuthor(document),
                pikabu.getTimestamp(document),
                pikabu.getTags(document),
                pikabu.getContent(document)
        );

        save(GSON.toJson(post), dir);
    }
}
