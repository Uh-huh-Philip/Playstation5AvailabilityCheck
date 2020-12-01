package network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static network.GetResultAsJson.getJsonObject;

public class NoelLeeming {

    private final JsonObject data = JsonParser.parseString("{\"sectionId\":8770662,\"skus\":[],\"timeframes\":[\"weekly\"],\"includeProductInterest\":\"view\",\"includeProductData\":true}").getAsJsonObject();
    private final String noelleemingSku;

    public NoelLeeming(String noelleemingSku) {
        this.noelleemingSku = noelleemingSku;
        data.getAsJsonArray("skus").add(noelleemingSku);
        System.out.println(data);
    }

    public JsonObject fetchData() {

        JsonObject jsonObject = null;

        try {
            String DESTIN_URL = "https://st.dynamicyield.com/pd";
            URL url = new URL(DESTIN_URL);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("content-type","application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(data.toString().getBytes());
            out.close();

            jsonObject = getJsonObject(urlConnection);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String checkAvailability(){
        String availability = "Unavailable";
        if (this.noelleemingSku != null){
            JsonObject product = fetchData();
            if (product!=null) {
                if (product.getAsJsonObject(noelleemingSku)
                        .getAsJsonObject("productData")
                        .get("in_stock")
                        .getAsString().equals("false")){
                    if (product.getAsJsonObject(noelleemingSku)
                            .getAsJsonObject("productData")
                            .get("add_to_cart")
                            .getAsString().equals("true"))
                        availability = "Preorder available";
                }else if (product.getAsJsonObject(noelleemingSku)
                        .getAsJsonObject("productData")
                        .get("in_stock")
                        .getAsString().equals("true"))
                    availability = "Available";
            }
        }
        return availability;
    }
}
