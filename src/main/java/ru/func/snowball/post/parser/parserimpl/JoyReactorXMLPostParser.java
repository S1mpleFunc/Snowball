package ru.func.snowball.post.parser.parserimpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import ru.func.snowball.post.parser.XMLPostParser;
import ru.func.snowball.post.parser.web.Recipient;
import ru.func.snowball.post.parser.web.RecipientJoyReactor;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class JoyReactorXMLPostParser implements XMLPostParser {

    private Document document;
    private Recipient joyReactor = new RecipientJoyReactor();

    @Override
    public String getPost(org.jsoup.nodes.Document document, String url, String dir) {

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
        timestamp.appendChild(this.document.createTextNode(joyReactor.getTimestamp(document)));
        post.appendChild(timestamp);

        /* Автор поста */
        Element author = this.document.createElement("author");
        author.appendChild(this.document.createTextNode(joyReactor.getAuthor(document)));
        post.appendChild(author);

        /* Теги */
        Element tags = this.document.createElement("tags");
        joyReactor.getTags(document).forEach(currentTag -> {
            Element tag = this.document.createElement("tag");
            tag.appendChild(this.document.createTextNode(currentTag.getTag()));
            tags.appendChild(tag);
        });
        post.appendChild(tags);

        /* Копирование поста */
        Element content = this.document.createElement("content");
        joyReactor.getContent(document).forEach(view -> {
            Element element = this.document.createElement(view.getTag());
            element.appendChild(this.document.createTextNode(view.getContent()));
            if (!view.getAttribute().isEmpty())
                element.setAttribute("type", view.getAttribute());
            content.appendChild(element);
        });
        post.appendChild(content);

        /* Сохранение XML файла */
        //if (!dir.isEmpty())
        //save(this.document, dir);

        DOMImplementationLS domImplementation = (DOMImplementationLS) this.document.getImplementation();
        LSSerializer lsSerializer = domImplementation.createLSSerializer();

        return lsSerializer.writeToString(this.document);
    }
}
