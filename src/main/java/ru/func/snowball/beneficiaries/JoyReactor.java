package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JoyReactor implements Pithy {
    @Override
    public List<URL> getPhotoFromWebSite(final Document document) throws IOException {

        List<URL> urls = new ArrayList<>();

        for (Element element : document.getElementsByClass("image").get(0).children())
            urls.add(new URL(element.getElementsByTag("img").attr("src")));

        return urls;
    }
}
