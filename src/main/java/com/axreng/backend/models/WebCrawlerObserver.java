package com.axreng.backend.models;

public interface WebCrawlerObserver {
    void onPageVisited(String url);

    void onError(String str);
}
