package com.axreng.backend.utils;

import com.axreng.backend.models.WebCrawlerObserver;

import java.util.HashSet;
import java.util.Set;

public class NotifyUtils {
    public static Set<WebCrawlerObserver> observers = new HashSet<>();

    public static void notifyObservers(String url) {
        for (WebCrawlerObserver observer : observers) {
            observer.onPageVisited(url);
        }
    }

    public static void notifyObservers(Set<String> urlsFound) {
        for (WebCrawlerObserver observer : observers) {
            observer.onFinished(urlsFound);
        }
    }

}
