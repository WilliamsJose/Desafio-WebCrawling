package com.axreng.backend.models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class AxrengFileWriter {

    public void createNewFile(String baseUrl, String keyword, Set<String> urlsFound) {

        try {
            //TODO checar se arquivo existe e criar copia: results_keyword_02.txt
            FileWriter file = new FileWriter("results_" + keyword + ".txt");

            file.write("Search starting with base URL '" + baseUrl + "' and keyword '" + keyword + "'" + System.lineSeparator());

            urlsFound.forEach(url -> {
                try {
                    file.write("Result found: " + url + System.lineSeparator());
                } catch (IOException e) {
                    System.out.println("Error during file write: " + e.getMessage());
                }
            });

            file.write("Search finished with " + urlsFound.size() + " results found");

            file.close();
            System.out.println("Successfully wrote to the file." + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
