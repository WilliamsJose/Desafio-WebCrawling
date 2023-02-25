package com.axreng.backend.models;

import java.util.Set;

public interface WebCrawlerObserver {
    void onPageVisited(String url);

    void onFinished(Set<String> urlsFound);
}
