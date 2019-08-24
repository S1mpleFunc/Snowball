package ru.func.snowball.parser;

import org.w3c.dom.Document;
import ru.func.snowball.PostParser;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public interface XMLPostParser extends PostParser {

    default void save(Document document, String dir) {
        /* Сохранение XML файла */
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new FileOutputStream(dir + "/" + System.currentTimeMillis() + ".xml"))
            );
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
