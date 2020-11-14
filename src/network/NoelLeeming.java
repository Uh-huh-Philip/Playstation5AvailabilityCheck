package network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class NoelLeeming {

    private static final String DESTIN_URL = "https://st.dynamicyield.com/pd";
    private JsonObject data = JsonParser.parseString("{\"sectionId\":8770662,\"skus\":[],\"timeframes\":[\"weekly\"],\"includeProductInterest\":\"view\",\"includeProductData\":true}").getAsJsonObject();

    public NoelLeeming(String noelleemingSku) {
        data.getAsJsonArray("skus").add(noelleemingSku);
        System.out.println(data);
    }

    public JsonObject fetchData() {

        JsonObject jsonObject = null;

        try {
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("content-type","application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(data.toString().getBytes());
            out.close();

            jsonObject = getJsonObject(urlConnection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
