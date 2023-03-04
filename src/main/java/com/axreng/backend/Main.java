package com.axreng.backend;

import com.axreng.backend.controllers.WebCrawler;
import com.axreng.backend.views.WebCrawlerView;

public class Main {
    public static void main(String[] args) {
        String BASE_URL = System.getenv("BASE_URL");
        String KEYWORD = System.getenv("KEYWORD");
        String strMaxResults = System.getenv("MAX_RESULTS");
        int MAX_RESULTS = strMaxResults != null && !strMaxResults.isBlank() ? Integer.parseInt(strMaxResults) : -1;

        WebCrawler crawler = new WebCrawler(BASE_URL, KEYWORD, MAX_RESULTS);
        crawler.addObserver(new WebCrawlerView());
        crawler.init();
    }
}
