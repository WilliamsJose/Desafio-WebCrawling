package com.axreng.backend.controllers;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkService {
    private String baseUrl;
    private Pattern linkPattern = Pattern.compile("<a.*href=\\\"(\\S*)\\\"", Pattern.CASE_INSENSITIVE);

    public LinkService (String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public Set<String> getLinks(String htmlPage) {
        Set<String> allObtainedLinks = new HashSet<>();
        Matcher matcher = linkPattern.matcher(htmlPage);

        // Garantir funcionamento caso a var ENV seja setada com ou sem '/'
        if(!baseUrl.endsWith("/")) {
            baseUrl.concat("/");
        }

        while(matcher.find()) {
            String link = matcher.group(1);

            if(!link.startsWith("http")) {
                String relativeLink = link.replace(baseUrl, "");
                boolean isValidLink = !(relativeLink.startsWith("#") || relativeLink.startsWith("mailto:") || relativeLink.startsWith("javascript:") || relativeLink.startsWith("tel:") || relativeLink.startsWith("ftp:"));

                if(link.startsWith("/") && isValidLink) {
                    allObtainedLinks.add(baseUrl + link.replaceFirst("/", ""));
                } else if(isValidLink) {
                    allObtainedLinks.add(baseUrl + link);
                }
            } else if (link.contains(baseUrl)) {
                allObtainedLinks.add(link);
            }
        }

        return allObtainedLinks;
    }
}
