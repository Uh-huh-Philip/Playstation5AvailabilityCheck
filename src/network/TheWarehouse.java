package network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class TheWarehouse {

    private String DESTIN_URL = "https://www.thewarehouse.co.nz/on/demandware.store/Sites-twl-Site/default/Product-GetAvailability?pid=%s&Quantity=1&inventoryListId=&Source=&VariationGroupIncluded=true&format=ajax'";

    public TheWarehouse(String noelleemingSku) {
        DESTIN_URL = String.format(DESTIN_URL, noelleemingSku);
    }

    public JsonObject fetchData() {

        JsonObject jsonObject = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

            jsonObject = getJsonObject(urlConnection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
