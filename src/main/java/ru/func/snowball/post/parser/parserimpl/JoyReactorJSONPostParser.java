package ru.func.snowball.post.parser.parserimpl;

import ru.func.snowball.post.Post;
import ru.func.snowball.post.parser.JSONPostParser;
import ru.func.snowball.post.parser.web.Recipient;
import ru.func.snowball.post.parser.web.RecipientJoyReactor;

public class JoyReactorJSONPostParser implements JSONPostParser {

    private Recipient joyReactor = new RecipientJoyReactor();

    @Override
    public void getPost(org.jsoup.nodes.Document document, String url, String dir) {

        save(GSON.toJson(new Post(url,
                joyReactor.getAuthor(document),
                joyReactor.getTimestamp(document),
                joyReactor.getTags(document),
                joyReactor.getContent(document)
        )), dir);
    }
}
