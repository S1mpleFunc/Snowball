package ru.func.snowball.beneficiaries;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.w3c.dom.Element;
import ru.func.snowball.Snowball;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Pikabu implements Pithy {

    @Override
    public List<Element> getPostFromWebSite(final Document document) {

        List<Element> elements = new ArrayList<>();
        org.w3c.dom.Document xmlDocument = Snowball.getDocument();

        /* Время создания поста */
        Element timestamp = xmlDocument.createElement("timestamp");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long time = 0;
        try {
            time = format.parse(document.getElementsByAttribute("datetime").get(0).attr("datetime")).getTime();
        } catch (ParseException ignored) {
        }
        timestamp.appendChild(xmlDocument.createTextNode(time + ""));
        elements.add(timestamp);

        /* Автор поста */
        Element author = xmlDocument.createElement("author");
        author.appendChild(xmlDocument.createTextNode(
                document.getElementsByAttribute("data-name").get(0).attr("data-name")
        ));
        elements.add(author);

        /* Теги */
        Element tags = xmlDocument.createElement("tags");
        for (org.jsoup.nodes.Element element : document.getElementsByClass("tags__tag")) {
            if (element.hasAttr("data-tag")) {
                Element tag = xmlDocument.createElement("tag");
                tag.appendChild(xmlDocument.createTextNode(element.attr("data-tag")));
                tags.appendChild(tag);
            }
        }
        elements.add(tags);

        /* Копирование поста */
        Element content = xmlDocument.createElement("content");
        for (org.jsoup.nodes.Element element : document.getElementsByClass("story__content-inner").get(0).children()) {
            if (element.children().size() > 0) {
                for (org.jsoup.nodes.Element child : element.children()) {
                    if (child.hasText()) {
                        Element text = xmlDocument.createElement("text");
                        text.appendChild(xmlDocument.createTextNode(child.text()));
                        content.appendChild(text);
                    }
                }
            }
            if (element.getElementsByAttribute("href").size() > 0) {
                Element image = xmlDocument.createElement("image");
                image.appendChild(xmlDocument.createTextNode(element.getElementsByAttribute("href").get(0).attr("href")));
                content.appendChild(image);
            }
        }
        elements.add(content);

        return elements;
    }
}
