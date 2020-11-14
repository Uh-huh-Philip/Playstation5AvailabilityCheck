package network;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class TheWarehouse {

    private String DESTIN_URL = "https://www.thewarehouse.co.nz/on/demandware.store/Sites-twl-Site/default/Product-GetAvailability?pid=%s&Quantity=1&inventoryListId=&Source=&VariationGroupIncluded=true&format=ajax'";
    private final String thewarehousePid;
    private final String userAgent;

    public TheWarehouse(String thewarehousePid, String userAgent) {
        this.thewarehousePid = thewarehousePid;
        this.userAgent = userAgent;
        DESTIN_URL = String.format(DESTIN_URL, this.thewarehousePid);
    }

    public JsonObject fetchData() {

        JsonObject jsonObject = null;

        try {
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("user-agent",this.userAgent);
            jsonObject = getJsonObject(urlConnection);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String checkAvailability() {
        String availability = "Unavailable";
        if (this.thewarehousePid != null) {
            JsonObject product = fetchData();
            if (product != null) {
                switch (product
                        .get("statusCode")
                        .getAsString()) {
                    case "PREORDER":
                        if (!product
                                .get("isCurrentlyUnavailableOnline")
                                .getAsBoolean())
                            availability = "Preorder available";
                        break;
                    case "IN_STOCK":
                        availability = "Available";
                        break;
                }
            }
        }
        return availability;
    }

}
