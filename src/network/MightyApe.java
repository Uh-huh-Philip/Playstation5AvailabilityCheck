package network;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class MightyApe {

    private final String DESTIN_URL;
    private final String userAgent;

    public MightyApe(String uRL, String userAgent) {
        this.DESTIN_URL = uRL;
        this.userAgent = userAgent;
    }

    public String fetchData() {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("user-agent",this.userAgent);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String s;
            while (((s = reader.readLine()) != null)) {
                result.append(s);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String checkAvailability() {
        String availability = "Unavailable";
        if (this.DESTIN_URL != null) {
            String result = fetchData();
            if (result.contains("stock-status instock"))
                availability = "Available";
            else if (result.contains("stock-status preorder"))
                availability = "Preorder available";
            else if (result.contains("stock-status backordered"))
                availability = "On backorder";
        }
        return availability;
    }

}
