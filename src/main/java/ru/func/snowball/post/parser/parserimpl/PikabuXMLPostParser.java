package ru.func.snowball.post.parser.parserimpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.func.snowball.post.parser.XMLPostParser;
import ru.func.snowball.post.parser.web.Recipient;
import ru.func.snowball.post.parser.web.RecipientPikabu;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PikabuXMLPostParser implements XMLPostParser {

    private Document document;
    private Recipient pikabu = new RecipientPikabu();

    @Override
    public void getPost(org.jsoup.nodes.Document document, String url, String dir) {

        try {
            this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Element post = this.document.createElement("post");

        this.document.appendChild(post);

        /* Добавляю тег с ссылкой на пост */
        Element elementUrl = this.document.createElement("url");
        elementUrl.appendChild(this.document.createTextNode(url));
        post.appendChild(elementUrl);

        /* Время создания поста */
        Element timestamp = this.document.createElement("timestamp");
        timestamp.appendChild(this.document.createTextNode(pikabu.getTimestamp(document)));
        post.appendChild(timestamp);

        /* Автор поста */
        Element author = this.document.createElement("author");
        author.appendChild(this.document.createTextNode(pikabu.getAuthor(document)));
        post.appendChild(author);

        /* Теги */
        Element tags = this.document.createElement("tags");
        pikabu.getTags(document).forEach(currentTag -> {
            Element tag = this.document.createElement("tag");
            tag.appendChild(this.document.createTextNode(currentTag.getTag()));
            tags.appendChild(tag);
        });
        post.appendChild(tags);

        /* Копирование поста */
        Element content = this.document.createElement("content");
        pikabu.getContent(document).forEach(view -> {
            Element element = this.document.createElement(view.getTag());
            element.appendChild(this.document.createTextNode(view.getContent()));
            if (!view.getAttribute().isEmpty())
                element.setAttribute("type", view.getAttribute());
            content.appendChild(element);
        });
        post.appendChild(content);

        save(this.document, dir);
    }
}
