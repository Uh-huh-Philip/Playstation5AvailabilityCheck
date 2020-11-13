package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.JsonObject;
import fetch.NoelLeeming;

public class test extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String noelleemingSku = "200475";
        NoelLeeming noelLeeming = new NoelLeeming(noelleemingSku);
        JsonObject noelLeemingData = noelLeeming.fetchData();
        String availability = "Unknown";
        if (noelLeemingData.getAsJsonObject(noelleemingSku)
                .getAsJsonObject("productData")
                .get("in_stock")
                .getAsString().equals("false")){
            if (noelLeemingData.getAsJsonObject(noelleemingSku)
                    .getAsJsonObject("productData")
                    .get("add_to_cart")
                    .getAsString().equals("false"))
                availability = "Unavailable";
            else if (noelLeemingData.getAsJsonObject(noelleemingSku)
                    .getAsJsonObject("productData")
                    .get("add_to_cart")
                    .getAsString().equals("true"))
                availability = "Preorder available";
        }else if (noelLeemingData.getAsJsonObject(noelleemingSku)
                .getAsJsonObject("productData")
                .get("in_stock")
                .getAsString().equals("true"))
            availability = "Available";
        resp.setContentType("text/plain");
        resp.getWriter().println(availability);
    }
}
