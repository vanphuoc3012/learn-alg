package com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.cache;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelCache {
    public static int MAX_LIVING_TIME_MILLIS = 600_000;
    private final ConcurrentHashMap<String, CacheItem> cache;
    private final CleanCacheTask task;
    private final Thread thread;

    public ParallelCache() {
        cache = new ConcurrentHashMap<>();
        task = new CleanCacheTask(this);
        thread = new Thread(task);
        thread.start();
    }

    public void put(String command, String response) {
        CacheItem item = new CacheItem(command, response);
        cache.put(command, item);
    }

    public String get(String command) {
        CacheItem item = cache.get(command);
        if (item == null) {
            return null;
        }

        item.setAccessDate(new Date());
        return item.getResponse();
    }

    public void cleanCache() {
        Date revisionDate = new Date();

        Iterator<CacheItem> iterator = cache.values().iterator();

        while (iterator.hasNext()) {
            CacheItem item = iterator.next();
            if (revisionDate.getTime() - item.getAccessDate().getTime() > MAX_LIVING_TIME_MILLIS) {
                iterator.remove();
            }
        }
    }

    public void shutdown() {
        thread.interrupt();
    }

    public int getItemCount() {
        return cache.size();
    }
}
