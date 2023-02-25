package com.axreng.backend.views;

import com.axreng.backend.models.WebCrawlerObserver;

import java.util.Set;

public class WebCrawlerView implements WebCrawlerObserver {
    @Override
    public void onPageVisited(String url) {
        System.out.println("Visited page: " + url);
    }

    @Override
    public void onFinished(Set<String> urlsFound) {
        urlsFound.forEach(url -> System.out.println("Result found: " + url));
    }
}

