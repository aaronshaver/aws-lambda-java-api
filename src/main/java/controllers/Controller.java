package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Controller implements RequestStreamHandler {
    public void handleRequest(
            InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        JsonObject responseBody = new JsonObject();
        JsonObject responseJson = new JsonObject();

        try {

            double latMin = -90;
            double latMax = 90;
            double longMin = -180;
            double longMax = 180;
            double lat = latMin + Math.random() * (latMax - latMin);
            double longitude = longMin + Math.random() * (longMax - longMin);

            List<List<String>> zoomLevels = new ArrayList<>();
            zoomLevels.add(Arrays.asList("trivial", "6"));
            zoomLevels.add(Arrays.asList("easy", "8"));
            zoomLevels.add(Arrays.asList("medium", "10"));
            zoomLevels.add(Arrays.asList("hard", "12"));
            zoomLevels.add(Arrays.asList("veryHard", "14"));
            zoomLevels.add(Arrays.asList("extremelyHard", "16"));
            zoomLevels.add(Arrays.asList("impossible", "18"));
            zoomLevels.add(Arrays.asList("areYouCrazy", "20"));

            for (int i = 0; i < zoomLevels.size(); i++) {
                String outputUrl = String.format(
                        "https://www.google.com/maps/@%s,%s,%sz/data=!3m1!1e3",
                        lat,
                        longitude,
                        zoomLevels.get(i).get(1)
                );
                responseBody.addProperty(String.format("%sUrl", zoomLevels.get(i).get(0)), outputUrl);
            }

            responseJson.addProperty("statusCode", 200);
            responseJson.addProperty("body", responseBody.toString());

        } catch (Exception ex) {
            responseJson.addProperty("statusCode", 400);
            responseJson.addProperty("exception", ex.toString());
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writer.write(responseJson.toString());
        writer.close();
    }
}
