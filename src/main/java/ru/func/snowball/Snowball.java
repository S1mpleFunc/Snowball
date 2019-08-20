package ru.func.snowball;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.func.snowball.beneficiaries.JoyReactor;
import ru.func.snowball.beneficiaries.Pikabu;
import ru.func.snowball.beneficiaries.Pithy;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Snowball {

    public static void main(String... args) throws IOException {
        /* Поиск фото на посте */
        Document document = Jsoup.connect(args[0]).get();
        Pithy pithy = new Pikabu();

        if (args[0].contains("joy.reactor"))
            pithy = new JoyReactor();
        else if (args[0].contains("pikabu"))
            pithy = new Pikabu();

        /* Скачивание фото */
        URL url = pithy.getPhotoFromWebSite(document);
        File downloadDir = new File(args[1] + "/" + (new Random().nextInt(9000) + 1000) + ".jpg");
        RenderedImage img = ImageIO.read(url);
        ImageIO.write(img, "jpg", downloadDir);

        System.out.println("Файл успешно скачан.");
    }
}
