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

public class Snowball {

    public static void main(String... args) throws IOException {

        final Document document = Jsoup.connect(args[0]).get();

        /* Поиск фото на посте */
        Pithy pithy = null;

        if (args[0].contains("joy.reactor"))
            pithy = new JoyReactor();
        else if (args[0].contains("pikabu"))
            pithy = new Pikabu();
        else
            System.out.println("Скачивание с данного сервера не поддерживается.");

        /* Скачивание фото */
        assert pithy != null;
        for (URL url : pithy.getPhotoFromWebSite(document)) {

            if (url.toString().isEmpty())
                continue;

            String formatName = url.toString().split("\\.")[url.toString().split("\\.").length - 1];

            File downloadDir = new File(
                    args[1] + "/" +
                    System.currentTimeMillis() +
                    "." +
                    formatName
            );
            RenderedImage img = ImageIO.read(url);
            ImageIO.write(img, formatName, downloadDir);
            System.out.println("Файл успешно скачан.");
        }
    }
}
