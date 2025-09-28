package org.softceramic.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    private Utils() {

    }

    public static String readFile(String filePath) {
        String string;
        try {
            string = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException exception) {
            throw new RuntimeException("{Utils: Error reading file " + filePath, exception);
        }
        return string;
    }
}
