package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class JoyReactor implements Pithy {
    @Override
    public URL getPhotoFromWebSite(final Document document) throws IOException {

        String string = "";

        for (Element element : document.getElementsByClass("image").get(0).children()) {
            string = element.getElementsByTag("img").attr("src");
            if (!string.isEmpty())
                break;
        }

        return new URL(string);
    }
}
