package org.example.reader.advanced;

import org.example.buffer.NewsBuffer;
import org.example.reader.basic.NewsTask;
import org.example.writer.NewsWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NewsAdvancedSystem implements Runnable {
    private String route;
    private NewsExecutor executor;
    private NewsBuffer buffer;
    private CountDownLatch latch = new CountDownLatch(1);

    public NewsAdvancedSystem(String route) {
        this.route = route;
        executor = new NewsExecutor(Runtime.getRuntime().availableProcessors());
        buffer = new NewsBuffer();
    }

    @Override
    public void run() {
        Path path = Paths.get(route);

        System.out.println("Starting news writer....");
        NewsWriter newsWriter = new NewsWriter(buffer);
        Thread t = new Thread(newsWriter);
        t.start();
        System.out.println("News writer started");

        try (
                InputStream in = Files.newInputStream(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String data[] = line.split(";");

                NewsTask task = new NewsTask(data[0], data[1], buffer);
                System.out.println("Task " + task.getName());
                executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Shutting down the executor.");
        executor.shutdown();
        t.interrupt();
        System.out.println("The system has finished.");
    }

    public void shutdown() {
        latch.countDown();
    }
}
