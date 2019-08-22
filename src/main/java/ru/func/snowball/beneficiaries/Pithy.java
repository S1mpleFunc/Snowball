package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface Pithy {

    List<URL> getPhotoFromWebSite(final Document document) throws IOException;
}
