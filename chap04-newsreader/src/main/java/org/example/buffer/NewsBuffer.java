package org.example.buffer;

import org.example.common.CommonInformationItem;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class NewsBuffer {
    private LinkedBlockingQueue<CommonInformationItem> buffer;
    private ConcurrentHashMap<String, String> storeItems;

    public NewsBuffer() {
        this.buffer = new LinkedBlockingQueue<>();
        this.storeItems = new ConcurrentHashMap<>();
    }

    public void add(CommonInformationItem item) {
        storeItems.compute(item.getId(), (id, oldSource) -> {
            if (oldSource == null) {
                buffer.add(item);
                return item.getSource();
            } else {
                System.out.println("Item " + item.getId() + " has been processed before");
                return oldSource;
            }
        });
    }

    public CommonInformationItem get() throws InterruptedException {
        return buffer.take();
    }
}
