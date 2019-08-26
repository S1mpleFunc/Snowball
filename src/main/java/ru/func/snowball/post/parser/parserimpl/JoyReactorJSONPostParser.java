package ru.func.snowball.post.parser.parserimpl;

import org.jsoup.nodes.Document;
import ru.func.snowball.post.Post;
import ru.func.snowball.post.parser.JSONPostParser;
import ru.func.snowball.post.parser.web.Recipient;
import ru.func.snowball.post.parser.web.RecipientJoyReactor;

public class JoyReactorJSONPostParser implements JSONPostParser {

    private Recipient joyReactor = new RecipientJoyReactor();

    @Override
    public String getPost(Document document, String url, String dir) {

        String post = GSON.toJson(new Post(url,
                joyReactor.getAuthor(document),
                joyReactor.getTimestamp(document),
                joyReactor.getTags(document),
                joyReactor.getContent(document)
        ));

        save(post, dir);

        return post;
    }
}
