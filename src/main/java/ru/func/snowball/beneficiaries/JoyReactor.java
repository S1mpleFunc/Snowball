package ru.func.snowball.beneficiaries;

import org.jsoup.nodes.Document;
import org.w3c.dom.Element;
import ru.func.snowball.Snowball;

import java.util.ArrayList;
import java.util.List;

public class JoyReactor implements Pithy {

    @Override
    public List<Element> getPostFromWebSite(final Document document) {

        List<Element> elements = new ArrayList<>();
        org.w3c.dom.Document xmlDocument = Snowball.getDocument();

        /* Время создания поста */
        Element timestamp = xmlDocument.createElement("timestamp");
        timestamp.appendChild(xmlDocument.createTextNode(
                document.getElementsByAttribute("data-time").get(0).attr("data-time")
        ));
        elements.add(timestamp);

        /* Автор поста */
        Element author = xmlDocument.createElement("author");
        author.appendChild(xmlDocument.createTextNode(
                document.getElementsByClass("avatar").get(0).attr("alt")
        ));
        elements.add(author);

        /* Теги */
        Element tags = xmlDocument.createElement("tags");
        for (org.jsoup.nodes.Element element : document.getElementsByClass("taglist").get(0).children()) {
            if (element.children().size() > 0 && !element.children().get(0).attr("title").isEmpty()) {
                Element tag = xmlDocument.createElement("tag");
                tag.appendChild(xmlDocument.createTextNode(element.children().get(0).attr("title")));
                tags.appendChild(tag);
            }
        }
        elements.add(tags);

        /* Копирование поста */
        Element content = xmlDocument.createElement("content");
        for (org.jsoup.nodes.Element element : document.getElementsByClass("image").get(0).parent().children()) {
            if (element.hasText()) {
                Element text = xmlDocument.createElement("text");
                text.appendChild(xmlDocument.createTextNode(element.text()));
                content.appendChild(text);
            } else if (element.hasClass("image")) {
                org.jsoup.nodes.Element child = element.getElementsByAttribute("src").get(0);
                Element frame = null;
                if (child.tagName().equals("img")) {
                    frame = xmlDocument.createElement("image");
                    frame.appendChild(xmlDocument.createTextNode(child.attr("src")));
                } else if (child.tagName().equals("iframe")) {
                    frame = xmlDocument.createElement("video");
                    frame.appendChild(xmlDocument.createTextNode(child.attr("src")));
                    frame.setAttribute("type", child.className().split("-")[0]);
                }
                if (frame != null)
                    content.appendChild(frame);
            }
        }
        elements.add(content);

        return elements;
    }
}
