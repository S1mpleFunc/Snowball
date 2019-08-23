package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;
import org.w3c.dom.Element;

import java.util.List;

public interface Pithy {

    List<Element> getPostFromWebSite(final Document document);

}
