package com.axreng.backend.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AxrengFileWriter {

    public void createNewFile(String baseUrl, String keyword, List<String> urlsFound) {
        int counter = 0;
        while (true) {
            try {
                String filename = counter > 0 ? "results_" + keyword + "_(" + counter + ").txt" : "results_" + keyword + ".txt";

                File file = new File(filename);
                if (file.exists()) {
                    counter++;
                    continue;
                }

                FileWriter newFile = new FileWriter(filename);

                newFile.write("Search starting with base URL '" + baseUrl + "' and keyword '" + keyword + "'" + System.lineSeparator());

                urlsFound.forEach(url -> {
                    try {
                        newFile.write("Result found: " + url + System.lineSeparator());
                    } catch (IOException e) {
                        System.out.println("Error during file write: " + e.getMessage());
                    }
                });

                newFile.write("Search finished with " + urlsFound.size() + " results found");

                newFile.close();
                System.out.println("Successfully wrote to the file." + System.lineSeparator());
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            return;
        }
    }
}
