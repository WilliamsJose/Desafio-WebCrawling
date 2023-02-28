package com.axreng.backend;

import com.axreng.backend.controllers.WebCrawler;
import com.axreng.backend.views.WebCrawlerView;

public class Main {
    public static void main(String[] args) {
        // TODO validar se tenho ao menos as variáveis BaseUrl e Keyword para iniciar
        WebCrawler crawler = new WebCrawler("http://hiring.axreng.com/", "four");
        crawler.addObserver(new WebCrawlerView());
        crawler.init();
    }
}
