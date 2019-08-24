package ru.func.snowball.parser.parserimpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.func.snowball.content.Image;
import ru.func.snowball.content.Text;
import ru.func.snowball.content.Video;
import ru.func.snowball.parser.XMLPostParser;
import ru.func.snowball.parser.web.Recipient;
import ru.func.snowball.parser.web.RecipientJoyReactor;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class JoyReactorXMLPostParser implements XMLPostParser {

    private Document document;
    private Recipient joyReactor = new RecipientJoyReactor();

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
            Element element = null;
            if (view instanceof Image) {
                element = this.document.createElement("image");
                element.appendChild(this.document.createTextNode(((Image) view).getContent()));
            } else if (view instanceof Video) {
                element = this.document.createElement("video");
                element.appendChild(this.document.createTextNode(((Video) view).getContent().getVideo()));
                element.setAttribute("type", ((Video) view).getContent().getType());
            } else if (view instanceof Text) {
                element = this.document.createElement("text");
                element.appendChild(this.document.createTextNode(((Text) view).getContent()));
            }
            if (element != null)
                content.appendChild(element);
        });
        post.appendChild(content);

        /* Сохранение XML файла */
        save(this.document, dir);
    }
}
