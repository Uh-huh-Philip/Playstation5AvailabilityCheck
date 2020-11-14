package network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class JbHifi {

    private final JsonObject data = new JsonObject();
    private final String jbhifiId;

    public JbHifi(String jbhifiId) {
        this.jbhifiId = jbhifiId;
        data.add("Ids", new JsonArray());
        data.getAsJsonArray("Ids").add(jbhifiId);
        System.out.println(data);
    }

    public JsonObject fetchData() {

        JsonObject jsonObject = null;

        try {
            String DESTIN_URL = "https://products.jbhifi.co.nz/product/get/id";
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("content-type","application/json; charset=UTF-8");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(data.toString().getBytes());
            out.close();

            jsonObject = getJsonObject(urlConnection);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String checkAvailability(){
        String availabilityOnline = "Unavailable";
        String availabilityInStore = "Unavailable";
        if (this.jbhifiId != null){
            JsonObject product = fetchData().getAsJsonObject("Result").getAsJsonArray("Products").get(0).getAsJsonObject();
            if (product!=null) {
                switch (product.get("DeliveryStatus").getAsString()){
                    case "In Stock":
                        availabilityOnline = "Available";
                        break;
                    case "Pre-Order":
                        availabilityOnline = "Preorder available";
                        break;
                }
                switch (product.get("PickupStatus").getAsString()){
                    case "In Stock":
                        availabilityInStore = ", Available";
                        break;
                    case "Pre-Order":
                        availabilityInStore = ", Preorder available";
                        break;
                }
            }
        }
        return String.format("%s online, %s in store", availabilityOnline, availabilityInStore);
    }
}
