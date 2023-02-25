package com.axreng.backend.controllers;

import com.axreng.backend.models.WebCrawlerObserver;
import com.axreng.backend.utils.NotifyUtils;

import java.util.concurrent.*;

public class WebCrawler {

    private int MAX_PAGES_TO_VISIT = -1;
    ExecutorService executor = new ThreadPoolExecutor(6, 12, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private String KEYWORD;
    private String BASE_URL;

    public WebCrawler(String baseUrl, String keyword, int maxPagesToVisit) {
        BASE_URL = baseUrl;
        KEYWORD = keyword;
        MAX_PAGES_TO_VISIT = maxPagesToVisit;
    }

    public WebCrawler(String baseUrl, String keyword) {
        BASE_URL = baseUrl;
        KEYWORD = keyword;
    }

    public void addObserver(WebCrawlerObserver observer) {
        NotifyUtils.observers.add(observer);
    }

    public void removeObserver(WebCrawlerObserver observer) {
        NotifyUtils.observers.remove(observer);
    }

    public void init() {
        executor.execute(new ProcessHTMLPage(new LinkService(BASE_URL), KEYWORD, MAX_PAGES_TO_VISIT));
        shutdownExecutor(executor);
    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

