package ru.func.snowball.post.serialize.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.func.snowball.post.Post;
import ru.func.snowball.post.serialize.PostSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// данная реализация не является истинной в последней инстанции,
// однако она делает ровно то, что нам нужно:
// преобразовывает Post в String (XML)

public class XmlSerializer implements PostSerializer {

    private DocumentBuilder documentBuilder;

    public XmlSerializer() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);

        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    @Override
    public String serialize(Post post) {
        Document document = generateDocument(post);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(baos);

            Transformer transformer = buildTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(dataOutputStream));

            dataOutputStream.flush();

            return baos.toString(StandardCharsets.UTF_8.name());
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Document generateDocument(Post post) {
        // вообще можно было не вручную сериализовывать,
        // а воспользоваться аннотациями. Но т.к. у нас тут
        // довольно специфичная спецификация, то приходится
        // делать вот так.

        Document document = documentBuilder.newDocument();

        Element rootElement = document.createElement("post");
        document.appendChild(rootElement);

        createElementAndAddToRoot(rootElement, "url", post.getUrl());
        createElementAndAddToRoot(rootElement, "timestamp", post.getTimestamp());
        createElementAndAddToRoot(rootElement, "author", post.getAuthor());

        Element tagsElement = document.createElement("tags");
        post.getTags().forEach(tag -> {
            Element tagElement = document.createElement("tag");
            tagElement.setTextContent(tag.getTag());
            tagsElement.appendChild(tagElement);
        });
        rootElement.appendChild(tagsElement);

        Element contentElement = document.createElement("content");
        post.getContent().forEach(content -> {
            Element element = document.createElement(content.getTag());
            if (content.getAttribute() != null && !content.getAttribute().trim().isEmpty()) {
                element.setAttribute("type", content.getAttribute());
            }
            element.setTextContent(content.getContent());
            contentElement.appendChild(element);
        });
        rootElement.appendChild(contentElement);

        return document;
    }

    private Transformer buildTransformer() throws TransformerConfigurationException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());

        return transformer;
    }

    private void createElementAndAddToRoot(Element root, String name, String content) {
        Element authorElement = root.getOwnerDocument().createElement(name);
        authorElement.setTextContent(content);
        root.appendChild(authorElement);
    }
}
