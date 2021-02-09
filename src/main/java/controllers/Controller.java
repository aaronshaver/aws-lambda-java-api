package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Controller implements RequestStreamHandler {
    public void handleRequest(
            InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        JsonObject responseBody = new JsonObject();
        JsonObject responseJson = new JsonObject();

        try {
            HashMap event = gson.fromJson(reader, HashMap.class);

            if (event.get("queryStringParameters") != null) {
                JsonObject queryStringParameters = gson.fromJson(event.get("queryStringParameters").toString(), JsonObject.class);
                if (queryStringParameters.get("zoomLevel") != null) {

                    int zoomLevel = Integer.parseInt(queryStringParameters.get("zoomLevel").toString());
                    String outputUrl = String.format(
                            "https://www.google.com/maps/@35.1500099,-122.2394121,%dz",
                            zoomLevel
                    );
                    responseBody.addProperty("url", outputUrl);
                    responseJson.addProperty("statusCode", 200);
                }
            }

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
