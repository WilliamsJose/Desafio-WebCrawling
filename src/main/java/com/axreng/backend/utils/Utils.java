package com.axreng.backend.utils;

public class Utils {

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
