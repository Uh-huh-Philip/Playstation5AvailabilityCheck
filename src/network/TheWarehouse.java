package network;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class TheWarehouse {

    private String DESTIN_URL = "https://www.thewarehouse.co.nz/on/demandware.store/Sites-twl-Site/default/Product-GetAvailability?pid=%s&Quantity=1&inventoryListId=&Source=&VariationGroupIncluded=true&format=ajax'";
    private final String thewarehousePid;

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
                    case "IN_STOCK":
                        availability = "Available";
                }
            }
        }
        return availability;
    }

}
