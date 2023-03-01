package com.axreng.backend.controllers;

import com.axreng.backend.utils.NotifyUtils;
import com.axreng.backend.utils.SharedLists;
import com.axreng.backend.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public class ProcessHTMLPage implements Runnable {

    private LinkService linkService;
    private String keyword;
    private String baseUrl;
    private int maxPagesToVisit;
    private Executor executor;

    public ProcessHTMLPage(LinkService linkService, String keyword, int maxPagesToVisit, Executor executor) {
        this.linkService = linkService;
        this.keyword = keyword;
        this.baseUrl = linkService.getBaseUrl();
        this.maxPagesToVisit = maxPagesToVisit;
        this.executor = executor;
    }

    @Override
    public void run() {
        handlePage(linkService.getBaseUrl());
    }

    private void handlePage(String link) {

        try {

            System.out.println(Thread.currentThread());

            if (!Utils.shouldExecute(SharedLists.visitedPages.size(), SharedLists.pagesToVisit.size(), maxPagesToVisit)) {
                return;
            }

            SharedLists.visitedPages.add(link);
            SharedLists.pagesToVisit.remove(link);
            NotifyUtils.notifyObservers(link);

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            boolean validHTML = connection.getContentType() != null && connection.getContentType().startsWith("text/html");

            if (!validHTML) {
                return;
            }

            InputStream inputStream = connection.getInputStream();
            String htmlPage = new String(inputStream.readAllBytes());

            if (htmlPage.toLowerCase().contains(keyword.toLowerCase())) {
                SharedLists.urlsFound.add(link);
            }

            Set<String> links = linkService.getLinks(htmlPage);

            for (String cLink : links) {
                if (!SharedLists.visitedPages.contains(cLink)) {
                    SharedLists.pagesToVisit.add(cLink);
                    try {
                        executor.execute(() -> handlePage(cLink));
                    } catch (RejectedExecutionException ex) {
                        handlePage(cLink);
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + link + " " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error connecting to " + link + " " + e.getMessage());
        }
    }
}
