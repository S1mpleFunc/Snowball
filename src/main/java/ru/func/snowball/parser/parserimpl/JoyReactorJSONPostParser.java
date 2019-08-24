package ru.func.snowball.parser.parserimpl;

import ru.func.snowball.Post;
import ru.func.snowball.parser.JSONPostParser;
import ru.func.snowball.parser.web.Recipient;
import ru.func.snowball.parser.web.RecipientJoyReactor;

public class JoyReactorJSONPostParser implements JSONPostParser {

    private Recipient joyReactor = new RecipientJoyReactor();

    @Override
    public void getPost(org.jsoup.nodes.Document document, String url, String dir) {
        Post post = new Post(
                url,
                joyReactor.getAuthor(document),
                joyReactor.getTimestamp(document),
                joyReactor.getTags(document),
                joyReactor.getContent(document)
        );

        save(GSON.toJson(post), dir);
    }
}
