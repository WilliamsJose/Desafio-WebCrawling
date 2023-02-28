package com.axreng.backend.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SharedLists {
    public static Set<String> visitedPages = Collections.synchronizedSet(new HashSet<>());
    public static Set<String> urlsFound = new HashSet<>();
    public static Set<String> pagesToVisit = Collections.synchronizedSet(new HashSet<>());
}
