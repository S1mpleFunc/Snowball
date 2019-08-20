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
        for (Element element : doc.getElementsByClass("image").get(0).children())
            urlUpload = element.getElementsByTag("img").attr("src");

        /* Скачивание фото */
        URL url = new URL(urlUpload);
        File downloadDir = new File(args[1] + "/" + (new Random().nextInt(9000) + 1000) + ".jpeg");
        RenderedImage img = ImageIO.read(url);
        ImageIO.write(img, "jpeg", downloadDir);

        System.out.println("Файл успешно скачан.");
    }
}
