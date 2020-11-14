package network;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;

public class GetResultAsJson {

    static JsonObject getJsonObject(HttpURLConnection urlConnection) throws IOException {
        JsonObject jsonObject = null;
        StringBuilder result = new StringBuilder();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String s;
        while (((s = reader.readLine()) != null)) {
            result.append(s);
        }
        reader.close();

        JsonElement element = JsonParser.parseString(result.toString());
        if (!(element instanceof JsonNull)) {
            jsonObject = (JsonObject) element;
        }

        System.out.println(jsonObject);
        return jsonObject;
    }
}
