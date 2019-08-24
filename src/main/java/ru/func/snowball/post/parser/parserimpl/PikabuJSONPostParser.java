package ru.func.snowball.post.parser.parserimpl;

import ru.func.snowball.post.Post;
import ru.func.snowball.post.parser.JSONPostParser;
import ru.func.snowball.post.parser.web.Recipient;
import ru.func.snowball.post.parser.web.RecipientPikabu;

public class PikabuJSONPostParser implements JSONPostParser {

    private Recipient pikabu = new RecipientPikabu();

    @Override
    public void getPost(org.jsoup.nodes.Document document, String url, String dir) {

        save(GSON.toJson(new Post(url,
                pikabu.getAuthor(document),
                pikabu.getTimestamp(document),
                pikabu.getTags(document),
                pikabu.getContent(document)
        )), dir);
    }
}
