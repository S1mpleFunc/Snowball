package ru.func.snowball.post.serialize.impl;

import com.google.gson.*;
import ru.func.snowball.post.Post;
import ru.func.snowball.post.content.Content;
import ru.func.snowball.post.serialize.PostSerializer;

import java.lang.reflect.Type;

public class JsonSerializer implements PostSerializer {

    private Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Content.class, new ContentJsonSerializer())
            .registerTypeAdapter(Post.class, new PostJsonSerializer())
            .create();

    @Override
    public String serialize(Post post) {
        return gson.toJson(post);
    }

    // это, на самом деле, плохо, что эти сериализаторы объявлены внутри класса

    public static class PostJsonSerializer implements com.google.gson.JsonSerializer<Post> {

        @Override
        public JsonElement serialize(Post post, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject rootObject = new JsonObject();

            rootObject.addProperty("url", post.getUrl());
            rootObject.addProperty("timestamp", post.getTimestamp());
            rootObject.addProperty("author", post.getAuthor());

            JsonArray tagsArray = new JsonArray();
            post.getTags().forEach(tag -> tagsArray.add(tag.getTag()));
            rootObject.add("tags", tagsArray);

            JsonArray contentArray = new JsonArray();
            post.getContent().forEach(content -> contentArray.add(jsonSerializationContext.serialize(content)));
            rootObject.add("content", contentArray);

            return rootObject;
        }
    }

    public static class ContentJsonSerializer implements com.google.gson.JsonSerializer<Content> {

        @Override
        public JsonElement serialize(Content content, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject rootObject = new JsonObject();

            rootObject.addProperty("type", content.getTag());
            rootObject.addProperty("content", content.getContent());

            if (content.getAttribute() != null && !content.getAttribute().trim().isEmpty())
                rootObject.addProperty("attribute", content.getAttribute());

            return rootObject;
        }
    }
}
