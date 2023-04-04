package com.axreng.backend.utils;

import com.axreng.backend.utils.NotifyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AxrengFileWriter {

    public void createNewFile(String baseUrl, String keyword, Set<String> urlsFound) {
        int counter = 0;

        List<String> sortedList = new ArrayList<>(urlsFound);
        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
        File file = null;

        while (true) {
            try {
                String userHome = System.getProperty("user.home") + File.separator + "Desktop";
                String filename = counter > 0 ? "results_" + keyword + "_(" + counter + ").txt" : "results_" + keyword + ".txt";

                file = new File(userHome + "/results", filename);
                if (file.exists()) {
                    counter++;
                    continue;
                }

                file.getParentFile().mkdirs();
                FileWriter newFile = new FileWriter(file.getPath());

                newFile.write("Search starting with base URL '" + baseUrl + "' and keyword '" + keyword + "'" + System.lineSeparator());

                sortedList.forEach(url -> {
                    try {
                        newFile.write("Result found: " + url + System.lineSeparator());
                    } catch (IOException e) {
                        System.out.println("Error during file write: " + e.getMessage());
                    }
                });

                newFile.write("Search finished with " + sortedList.size() + " results found");

                newFile.close();
                System.out.println("Successfully wrote to the file " + file.getPath() + System.lineSeparator());
            } catch (SecurityException se) {
                NotifyUtils.notifyError("System not permit creating file directory, please create " + file.getPath() + " directory and run again. " + se.getMessage());
            } catch (IOException e) {
                NotifyUtils.notifyError("An error occurred: " + e.getMessage());
            }
            return;
        }
    }
}
