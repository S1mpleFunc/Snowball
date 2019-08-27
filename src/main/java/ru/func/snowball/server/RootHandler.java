package ru.func.snowball.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.func.snowball.post.Post;
import ru.func.snowball.post.parser.PostParser;
import ru.func.snowball.post.parser.web.RecipientJoyReactor;
import ru.func.snowball.post.parser.web.RecipientPikabu;
import ru.func.snowball.post.serialize.PostSerializer;
import ru.func.snowball.post.serialize.impl.JsonSerializer;
import ru.func.snowball.post.serialize.impl.XmlSerializer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> query = queryToMap(exchange.getRequestURI().getQuery());

        final String postUrl = query.get("post");
        if (postUrl == null || postUrl.trim().isEmpty()) {
            exchange.sendResponseHeaders(400, 0);
            return;
        }

        final String type = query.getOrDefault("type", "xml");

        PostParser postParser = buildParser(postUrl);
        Post post = postParser.getPost(postUrl);

        PostSerializer serializer = buildSerializer(type);
        Objects.requireNonNull(serializer);
        String outputData = serializer.serialize(post);

        setContentType(type, exchange);

        byte[] dataBytes = outputData.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, dataBytes.length);
        exchange.getResponseBody().write(dataBytes);
        exchange.getResponseBody().flush();
        exchange.close();
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();

        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            result.put(entry[0], entry.length > 1 ? entry[1] : "");
        }

        return result;
    }

    private PostParser buildParser(String url) {
        return new PostParser(
                url.contains("joyreactor") ?
                        new RecipientJoyReactor() :
                        new RecipientPikabu()
        );
    }

    private PostSerializer buildSerializer(String type) {
        if (type.equalsIgnoreCase("xml")) {
            try {
                return new XmlSerializer();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new JsonSerializer();
        }
    }

    private void setContentType(String type, HttpExchange exchange) {
        exchange.getResponseHeaders().add("Content-Type", type.equalsIgnoreCase("xml") ?
                "application/xml;charset=utf-8" :
                "application/json;charset=utf-8"
        );
    }
}
