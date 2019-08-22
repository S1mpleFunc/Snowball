package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Pikabu implements Pithy {

    @Override
    public List<URL> getPhotoFromWebSite(final Document document) throws IOException {

        List<URL> urls = new ArrayList<>();

        for (Element e : document.getElementsByTag("img"))
            if (e.hasAttr("data-large-image"))
                urls.add(new URL(e.attr("data-src")));

        return urls;
    }
}
