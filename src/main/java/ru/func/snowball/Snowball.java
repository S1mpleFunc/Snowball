package ru.func.snowball;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.func.snowball.beneficiaries.JoyReactor;
import ru.func.snowball.beneficiaries.Pikabu;
import ru.func.snowball.beneficiaries.Pithy;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class Snowball {

    private static Document document;

    public static void main(String... args) throws IOException, ParserConfigurationException, TransformerException {

        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element post = document.createElement("post");

        document.appendChild(post);

        /* Добавляю тег с ссылкой на пост */
        Element url = document.createElement("url");
        url.appendChild(document.createTextNode(args[0]));
        post.appendChild(url);

        /* Определение сайта */
        Pithy pithy = null;

        if (args[0].contains("joy.reactor"))
            pithy = new JoyReactor();
        else if (args[0].contains("pikabu"))
            pithy = new Pikabu();
        else
            System.out.println("Скачивание поста с данного сервера не поддерживается.");

        /* Добавление всех элементов в корень XML */
        assert pithy != null;
        pithy.getPostFromWebSite(Jsoup.connect(args[0]).get()).forEach(post::appendChild);

        /* Сохранение XML файла */
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(
                new DOMSource(document),
                new StreamResult(new FileOutputStream(args[1] + "/" + System.currentTimeMillis() + ".xml"))
        );

        System.out.println("Пост ускачно скачан.");
    }

    public static Document getDocument() {
        return document;
    }
}
