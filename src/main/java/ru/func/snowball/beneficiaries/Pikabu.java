package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class Pikabu implements Pithy {

    @Override
    public URL getPhotoFromWebSite(final Document document) throws IOException {

        String string = "";

        for (Element e : document.getElementsByTag("img")) {
            if (e.hasAttr("data-large-image")) {
                string = e.attr("data-src");
                if (!string.isEmpty())
                    break;
            }
        }

        return new URL(string);
    }
}
