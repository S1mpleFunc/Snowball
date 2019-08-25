package ru.func.snowball;

import org.jsoup.Jsoup;
import ru.func.snowball.post.parser.parserimpl.JoyReactorJSONPostParser;
import ru.func.snowball.post.parser.parserimpl.JoyReactorXMLPostParser;
import ru.func.snowball.post.parser.parserimpl.PikabuJSONPostParser;
import ru.func.snowball.post.parser.parserimpl.PikabuXMLPostParser;
import ru.func.snowball.post.PostParser;

import java.io.IOException;

public class Snowball {

    public static void main(String... args) throws IOException {

        PostParser postParser = null;
        if (args.length > 2 && args[2].equals("json")) {
            if (args[0].contains("reactor")) {
                postParser = new JoyReactorJSONPostParser();
            } else if (args[0].contains("pikabu")) {
                postParser = new PikabuJSONPostParser();
            }
        } else {
            if (args[0].contains("reactor")) {
                postParser = new JoyReactorXMLPostParser();
            } else if (args[0].contains("pikabu")) {
                postParser = new PikabuXMLPostParser();
            }
        }

        assert postParser != null;
        postParser.getPost(Jsoup.connect(args[0]).get(), args[0], args[1]);

        System.out.println("Пост ускачно скачан.");
    }
}
