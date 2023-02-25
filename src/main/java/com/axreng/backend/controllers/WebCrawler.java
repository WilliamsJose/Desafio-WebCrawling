package com.axreng.backend.controllers;

import com.axreng.backend.models.AxrengFileWriter;
import com.axreng.backend.models.WebCrawlerObserver;
import com.axreng.backend.utils.NotifyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    private int MAX_PAGES_TO_VISIT = -1;
    //private final ExecutorService executor = Executors.newFixedThreadPool(2);
    ExecutorService executor = new ThreadPoolExecutor(6, 12, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//    private Set<WebCrawlerObserver> observers = new HashSet<>();
    private String KEYWORD;
    private String BASE_URL;
//    private Set<String> urlsFound = new HashSet<>();
//    private Set<String> visitedPages = new HashSet<>();
//    private Set<String> pagesToVisit = new HashSet<>();
//    private Pattern linkPattern = Pattern.compile("<a.*href=\\\"(\\S*)\\\"", Pattern.CASE_INSENSITIVE);
//    private boolean isExecutorShutdown = false;

    public WebCrawler(String baseUrl, String keyword, int maxPagesToVisit) {
        BASE_URL = baseUrl;
        KEYWORD = keyword;
        MAX_PAGES_TO_VISIT = maxPagesToVisit;
    }

    public WebCrawler(String baseUrl, String keyword) {
        BASE_URL = baseUrl;
        KEYWORD = keyword;
    }

//    public void crawl() {
//        try {
//            pagesToVisit.add(BASE_URL);
//            handlePageLinks(BASE_URL);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//    }

    public void addObserver(WebCrawlerObserver observer) {
        NotifyUtils.observers.add(observer);
    }

    public void removeObserver(WebCrawlerObserver observer) {
        NotifyUtils.observers.remove(observer);
    }

    public void init() {
        executor.execute(new ProcessHTMLPage(new LinkService(BASE_URL), KEYWORD, MAX_PAGES_TO_VISIT));
        shutdownExecutor(executor);
    }

//    private void handlePageLinks(String currentUrl) {
////        visitedPages.add(currentUrl);
//
////        if (!shouldExecute()) {
////            shutdownExecutor(executor);
////            return;
////        }
//
////        pagesToVisit.remove(currentUrl);
//
//        try {
//            executor.execute(() -> {
//                try {
////                    URL url = new URL(currentUrl);
////                    URLConnection connection = url.openConnection();
////
////                    boolean checkConnection = connection.getContentType() != null && connection.getContentType().startsWith("text/html");
////
////                    if (!checkConnection) {
////                        return;
////                    }
////
////                    InputStream inputStream = connection.getInputStream();
////                    String htmlPage = new String(inputStream.readAllBytes());
////
////                    if (htmlPage.contains(KEYWORD)) {
////                        urlsFound.add(currentUrl);
////                    }
////
////                    Set<String> links = getLinks(htmlPage);
////
////                    for (String link : links) {
////                        if (!visitedPages.contains(link)) {
////                            pagesToVisit.add(link);
////                            handlePageLinks(link);
////                        }
////                    }
////
////                    notifyObservers(currentUrl);
////                } catch (MalformedURLException e) {
////                    System.out.println("Malformed URL: " + currentUrl + " " + e.getMessage());
////                } catch (IOException e) {
////                    System.out.println("Error connecting to " + currentUrl + " " + e.getMessage());
////                } finally {
////                    new AxrengFileWriter().createNewFile(BASE_URL, KEYWORD, urlsFound);
////                }
//            });
//        } catch (RejectedExecutionException e) {
//            System.out.println("deu merda " + e.getMessage());
//        } finally {
//            shutdownExecutor(executor);
//        }
//
//    }

//    private boolean shouldExecute() {
//        if(MAX_PAGES_TO_VISIT != -1) {
//            if(visitedPages.size() >= MAX_PAGES_TO_VISIT) {
//                return false;
//            }
//        }
//
//        if (pagesToVisit.size() == 0) {
//            return false;
//        }
//        return true;
//    }

//    private Set<String> getLinks(String htmlPage) {
//        Set<String> allObtainedLinks = new HashSet<>();
//        Matcher matcher = linkPattern.matcher(htmlPage);
//
//        // Garantir funcionamento caso a var ENV seja setada com '/' ou sem '/'
//        if(!BASE_URL.endsWith("/")) {
//            BASE_URL.concat("/");
//        }
//
//        while(matcher.find()) {
//            String link = matcher.group(1);
//
//            if(!link.startsWith("http")) {
//                String relativeLink = link.replace(BASE_URL, "");
//                boolean isValidLink = !(relativeLink.startsWith("#") || relativeLink.startsWith("mailto:") || relativeLink.startsWith("javascript:") || relativeLink.startsWith("tel:") || relativeLink.startsWith("ftp:"));
//
//                if(link.startsWith("/") && isValidLink) {
//                    allObtainedLinks.add(BASE_URL + link.replaceFirst("/", ""));
//                } else if(isValidLink) {
//                    allObtainedLinks.add(BASE_URL + link);
//                }
//            } else if (link.contains(BASE_URL)) {
//                allObtainedLinks.add(link);
//            }
//        }
//
//        return allObtainedLinks;
//    }

//    private void notifyObservers(String url) {
//        for (WebCrawlerObserver observer : observers) {
//            observer.onPageVisited(url);
//        }
//    }
//
//    private void notifyObservers(Set<String> urlsFound) {
//        for (WebCrawlerObserver observer : observers) {
//            observer.onFinished(urlsFound);
//        }
//    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
//        isExecutorShutdown = true;
    }
}

