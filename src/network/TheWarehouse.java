package network;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class TheWarehouse {

    private String DESTIN_URL = "https://www.thewarehouse.co.nz/on/demandware.store/Sites-twl-Site/default/Product-GetAvailability?pid=%s&Quantity=1&inventoryListId=&Source=&VariationGroupIncluded=true&format=ajax'";
    private String thewarehousePid;

    public TheWarehouse(String thewarehousePid) {
        this.thewarehousePid = thewarehousePid;
        DESTIN_URL = String.format(DESTIN_URL, this.thewarehousePid);
    }

    public JsonObject fetchData() {

        JsonObject jsonObject = null;

        try {
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            jsonObject = getJsonObject(urlConnection);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String checkAvailability() {
        if (this.thewarehousePid != null) {
            JsonObject product = fetchData();
            if (product != null) {
                if (product
                        .get("statusCode")
                        .getAsString().equals("false")) {
                    if (product
                            .get("isCurrentlyUnavailableOnline")
                            .getAsBoolean())
                        return "Unavailable";
                    else
                        return "Preorder available";
                } else if (product.
                        get("statusCode")
                        .getAsString().equals("IN_STOCK"))
                    return "Available";
                else if (product.
                        get("statusCode")
                        .getAsString().equals("NOT_AVAILABLE"))
                    return "Unavailable";
            } else return "Unknown";
        }
        return "Unknown";
    }

}
