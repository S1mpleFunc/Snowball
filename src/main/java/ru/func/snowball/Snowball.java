package ru.func.snowball;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Snowball {

    public static void main(String... args) throws IOException {
        /* Поиск фото на посте */
        Document doc = Jsoup.connect(args[0]).get();
        String urlUpload = "";
        for (Element e : doc.getElementsByTag("img")) {
            if (e.hasAttr("data-large-image")) {
                System.out.println(e.attr("data-src"));
                if (!urlUpload.isEmpty())
                    break;
            }
        }

        /* Скачивание фото */
        URL url = new URL(urlUpload);
        File downloadDir = new File(args[1] + "/" + (new Random().nextInt(9000) + 1000) + ".jpg");
        RenderedImage img = ImageIO.read(url);
        ImageIO.write(img, "jpg", downloadDir);

        System.out.println("Файл успешно скачан.");
    }
}
