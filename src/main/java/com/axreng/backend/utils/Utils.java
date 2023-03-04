package com.axreng.backend.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class Utils {

    public static void validateParameters(String url, String keyword, int maxPagesToVisit) {
        try {
            URI baseUrl = new URI(url);
            if(baseUrl.getRawPath() == null || baseUrl.getRawPath().isBlank() || keyword == null || keyword.isBlank()) {
                throw new NullPointerException();
            }
        } catch (URISyntaxException uriError) {
            NotifyUtils.notifyError("Unexpected URL: " + uriError.getMessage());
            System.exit(1);
        } catch (NullPointerException npeError) {
            NotifyUtils.notifyError("URL or KEYWORD not provided: " + npeError.getMessage());
            System.exit(1);
        }

        if(maxPagesToVisit != -1 && maxPagesToVisit < 1) {
            NotifyUtils.notifyError("Max pages to visit length invalid.");
            System.exit(1);
        }
    }

    public static boolean shouldExecute(int visitedPageSize, int pagesToVisitSize, int maxPagesToVisit) {
        if(maxPagesToVisit != -1) {
            if(visitedPageSize >= maxPagesToVisit) {
                return false;
            }
        }

        if (pagesToVisitSize == 0) {
            return false;
        }
        return true;
    }

    public static void waitFinish(int maxPagesToVisit) {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(maxPagesToVisit != -1 && SharedLists.visitedPages.size() >= maxPagesToVisit) {
                return;
            }
            if(SharedLists.pagesToVisit.iterator().hasNext() && SharedLists.visitedPages.size() > 0) {
                continue;
            }
            return;
        }
    }
}
