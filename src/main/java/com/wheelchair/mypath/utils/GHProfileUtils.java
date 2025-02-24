package com.wheelchair.mypath.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.jackson.Jackson;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.graphhopper.util.Helper.readJSONFileWithoutComments;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
public class GHProfileUtils {

    public static CustomModel loadCustomModel(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            if (is == null)
                throw new IllegalArgumentException("There is no custom model '" + filePath + "'");
            String json = readJSONFileWithoutComments(new InputStreamReader(is));
            ObjectMapper objectMapper = Jackson.newObjectMapper();
            return objectMapper.readValue(json, CustomModel.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load custom model '" + filePath + "'");
        }
    }
}
