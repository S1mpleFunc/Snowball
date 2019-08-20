package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public interface Pithy {

    URL getPhotoFromWebSite(final Document document) throws IOException;
}
