package com.wheelchair.mypath.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.wheelchair.mypath.constants.Constants.PBF_PATH;
import static java.nio.file.Path.of;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
public class Utils {

    //get inclusive sub array [start, end]
    public static <T> List<T> getSubArray(List<T> arrayList, int start, int end) {

        return new ArrayList<>(arrayList.subList(start, end+1));
    }

    public static void deleteDirectory(String filePath) throws IOException {
        Path directory =  of(filePath);

        Files.walk(directory)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete " + path, e);
                    }
                });
    }

    public static String downloadFile(String url, String downloadPath) throws IOException {
        Files.copy(new URL(url).openStream(), of(downloadPath), REPLACE_EXISTING);

        return PBF_PATH;
    }
}
