package me.jsj.item7.cache;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest {

    @Test
    void cacheByInteger() throws InterruptedException {
        PostRepository postRepository = new PostRepository();
        Integer p1 = 1;
        postRepository.getPostIdByInteger(p1);

        assertThat(postRepository.getCacheByIntegerKey()).isNotEmpty();

        System.out.println("run GC");
        System.gc();
        System.out.println("wait");
        Thread.sleep(3000);

        //Integer의 경우 JVM에서 캐싱하기 때문에 GC에 의해 삭제되지 않는다. (primitive, String 또한 동일)
        assertThat(postRepository.getCacheByIntegerKey()).isNotEmpty();
    }

    @Test
    void cacheByKey() throws InterruptedException {
        PostRepository postRepository = new PostRepository();

        //참조가 실행이 끝날 때까지 진행되는 경우 -> GC에 의해 제거되지 않기에 WeakHashMap에서 그대로 유지된다.
        CacheKey key = new CacheKey(1);
        postRepository.getPostIdByCacheKey(key);

        //참조가 해당 메소드 내에서 끊기는 경우 -> GC에 의해 제거되어 WeakHashMap에서도 제거된다.
//        postRepository.getPostIdByCacheKey(new CacheKey(1));

        assertThat(postRepository.getCache()).isNotEmpty();

        System.out.println("run GC");
        System.gc();
        System.out.println("wait");
        Thread.sleep(3000);

        //Key에 대한 참조가 지속되어 GC로부터 살아남게 되고, WeakHashMap에서도 그대로 유지가 된다.
        assertThat(postRepository.getCache()).isNotEmpty();
    }

    @Test
    void backgroundThread() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        PostRepository postRepository = new PostRepository();
        CacheKey key = new CacheKey(1);
        postRepository.getPostIdByCacheKey(key);

        Runnable removeOldCache = () -> {
            System.out.println("running removeOldCache task");
            Map<CacheKey, Post> cache = postRepository.getCache();
            Set<CacheKey> cacheKeys = cache.keySet();
            Optional<CacheKey> findKey = cacheKeys.stream().min(Comparator.comparing(CacheKey::getCreated));
            findKey.ifPresent(k -> {
                System.out.println("removing: " + k);
                cache.remove(k);
            });
        };

        System.out.println("The time is : " + new Date());

        //초기 1초 이후 3초 주기로 쓰레드 실행
        executor.scheduleAtFixedRate(removeOldCache, 1, 3, TimeUnit.SECONDS);

        Thread.sleep(20000L);

        executor.shutdown();
    }
}