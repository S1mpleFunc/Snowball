package ru.func.snowball.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.func.snowball.PostParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public interface JSONPostParser extends PostParser {

    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    default void save(String doc, String dir) {
        /* Сохранение JSON файла */
        try(OutputStreamWriter writer = new FileWriter(dir + "/" + System.currentTimeMillis() + ".json")) {
            writer.write(doc);
            writer.flush();
        }
        catch(IOException ignored) {
        }
    }
}
