package com.axreng.backend.views;

import com.axreng.backend.models.WebCrawlerObserver;

public class WebCrawlerView implements WebCrawlerObserver {
    @Override
    public void onPageVisited(String url) {
        System.out.println("Visited page: " + url);
    }

    @Override
    public void onError(String str) {
        System.out.println("An error occurred: " + str);
    }

}

