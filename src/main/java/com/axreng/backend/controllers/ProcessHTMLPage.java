package com.axreng.backend.controllers;

import com.axreng.backend.models.AxrengFileWriter;
import com.axreng.backend.models.WebCrawlerObserver;
import com.axreng.backend.utils.NotifyUtils;
import com.axreng.backend.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessHTMLPage implements Runnable {

    private LinkService linkService;
    private String keyword;
    private String baseUrl;
    private Set<String> visitedPages;
    private Set<String> urlsFound;
    private Set<String> pagesToVisit;
    private int maxPagesToVisit;

    public ProcessHTMLPage(LinkService linkService, String keyword, int maxPagesToVisit) {
        this.linkService = linkService;
        this.keyword = keyword;
        this.baseUrl = linkService.getBaseUrl();
        this.urlsFound = new HashSet<>();
        this.visitedPages = new HashSet<>();
        this.pagesToVisit = new HashSet<>();
        this.maxPagesToVisit = maxPagesToVisit;
    }

    @Override
    public void run() {
        String baseUrl = linkService.getBaseUrl();
        handlePage(baseUrl);
    }

    private void handlePage(String link) {

        try {
            if(visitedPages.size() == 0) {
                pagesToVisit.add(link);
            }

            if (!Utils.shouldExecute(visitedPages.size(), pagesToVisit.size(), maxPagesToVisit)) {
                return;
            }

            pagesToVisit.remove(link);
            NotifyUtils.notifyObservers(link);
            visitedPages.add(link);

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            boolean checkConnection = connection.getContentType() != null && connection.getContentType().startsWith("text/html");

            if (!checkConnection) {
                return;
            }

            InputStream inputStream = connection.getInputStream();
            String htmlPage = new String(inputStream.readAllBytes());

            if (htmlPage.contains(keyword)) {
                urlsFound.add(link);
                new AxrengFileWriter().createNewFile(baseUrl, keyword, urlsFound);
            }

            Set<String> links = linkService.getLinks(htmlPage);

            for (String cLink : links) {
                if (!visitedPages.contains(cLink)) {
                    pagesToVisit.add(cLink);
                    handlePage(cLink);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + link + " " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error connecting to " + link + " " + e.getMessage());
        }
    }
}
