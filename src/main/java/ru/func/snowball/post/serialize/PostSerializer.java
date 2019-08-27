package ru.func.snowball.post.serialize;

import ru.func.snowball.post.Post;

public interface PostSerializer {

    String serialize(Post post);
}
