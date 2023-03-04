package com.axreng.backend.controllers;

import com.axreng.backend.models.AxrengFileWriter;
import com.axreng.backend.models.WebCrawlerObserver;
import com.axreng.backend.utils.NotifyUtils;
import com.axreng.backend.utils.SharedLists;
import com.axreng.backend.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebCrawler {

    private int MAX_PAGES_TO_VISIT = -1;
    private String KEYWORD;
    private String BASE_URL;
    private int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() / 2 : 1;
    ExecutorService executor = Executors.newFixedThreadPool(NUM_PROCESSORS);

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
        Utils.validateParameters(BASE_URL, KEYWORD, MAX_PAGES_TO_VISIT);

        SharedLists.pagesToVisit.add(BASE_URL);

        executor.execute(new ProcessHTMLPage(new LinkService(BASE_URL), KEYWORD, MAX_PAGES_TO_VISIT, executor));

        Utils.waitFinish(MAX_PAGES_TO_VISIT);

        shutdownExecutor(executor);

        new AxrengFileWriter().createNewFile(BASE_URL, KEYWORD, SharedLists.urlsFound);
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

