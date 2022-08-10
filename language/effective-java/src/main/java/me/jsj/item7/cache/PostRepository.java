package me.jsj.item7.cache;

import java.util.Map;
import java.util.WeakHashMap;

public class PostRepository {

    private Map<Integer, Post> cacheByIntegerKey;
    private Map<CacheKey, Post> cache;

    public PostRepository() {
        cacheByIntegerKey = new WeakHashMap<>();
        cache = new WeakHashMap<>();
    }

    public Post getPostIdByInteger(Integer id) {
        if (cacheByIntegerKey.containsKey(id)) return cacheByIntegerKey.get(id);
        else {
            Post post = new Post();
            cacheByIntegerKey.put(id, post);
            return post;
        }
    }

    public Post getPostIdByCacheKey(CacheKey key) {
        if (cache.containsKey(key)) return cache.get(key);
        else {
            Post post = new Post();
            cache.put(key, post);
            return post;
        }
    }

    public Map<Integer, Post> getCacheByIntegerKey() {
        return cacheByIntegerKey;
    }

    public Map<CacheKey, Post> getCache() {
        return cache;
    }
}
