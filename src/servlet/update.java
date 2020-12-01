package servlet;

import com.google.appengine.api.datastore.Entity;
import model.Product;
import network.JbHifi;
import network.MightyApe;
import network.NoelLeeming;
import network.TheWarehouse;
import util.DataUtility;
import util.EmailUtility;
import util.RandomUserAgent;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class update extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String userAgent = RandomUserAgent.getRandomUserAgent();
        List<Entity> stockStatus = new ArrayList<>();

        resp.setContentType("text/plain");
        for (Entity productEntity: DataUtility.retrieveEntities("Product")){
            Product product = new Product(productEntity.getProperty("productName").toString(),
                    productEntity.getProperty("thewarehousePid").toString(),
                    productEntity.getProperty("noelleemingSku").toString(),
                    productEntity.getProperty("jbhifiId").toString(),
                    productEntity.getProperty("mightyApeUrl").toString());

            resp.getWriter().println(product.getProductName());

            String noelleemingSku = product.getNoelleemingSku();
            NoelLeeming noelLeeming = new NoelLeeming(noelleemingSku);
            String noelLeemingAvailability = noelLeeming.checkAvailability();
            resp.getWriter().println("Noel Leeming: " + noelLeemingAvailability);

            String thewarehousePid = product.getThewarehousePid();
            TheWarehouse theWarehouse = new TheWarehouse(thewarehousePid, userAgent);
            String theWarehouseAvailability = theWarehouse.checkAvailability();
            resp.getWriter().println("The Warehouse: " + theWarehouseAvailability);

            String jbhifiId = product.getJbhifiId();
            JbHifi jbHifi = new JbHifi(jbhifiId);
            String jbHifiAvailability = jbHifi.checkAvailability();
            resp.getWriter().println("JB Hi-Fi: " + jbHifiAvailability);

            MightyApe mightyApe = new MightyApe(product.getMightyApeUrl(), userAgent);
            String mightyApeAvailability = mightyApe.checkAvailability();
            resp.getWriter().println("MightyApe: " + mightyApeAvailability);

            stockStatus.addAll(Arrays.asList(
                    DataUtility.stockStatusBuilder(productEntity.getKey(),"Noel Leeming", noelLeemingAvailability),
                    DataUtility.stockStatusBuilder(productEntity.getKey(),"The Warehouse", theWarehouseAvailability),
                    DataUtility.stockStatusBuilder(productEntity.getKey(),"JB Hi-Fi", jbHifiAvailability),
                    DataUtility.stockStatusBuilder(productEntity.getKey(),"MightyApe", mightyApeAvailability)));
            if (noelLeemingAvailability != null
                    && theWarehouseAvailability != null
                    && jbHifiAvailability != null
                    && mightyApeAvailability != null) {
                if (noelLeemingAvailability.equals("Available")
                        || noelLeemingAvailability.equals("Preorder available")
                        || theWarehouseAvailability.equals("Available")
                        || theWarehouseAvailability.equals("Preorder available")
                        || jbHifiAvailability.contains("Available")
                        || jbHifiAvailability.contains("Preorder available")
                        || mightyApeAvailability.equals("Available")
                        || mightyApeAvailability.equals("Preorder available")
                        || mightyApeAvailability.equals("On backorder")) {
                    if (!(boolean) productEntity.getProperty("online")) {
                        productEntity.setProperty("online", true);
                        DataUtility.updateStock(productEntity);
                        String emailText = "Noel Leeming: " + noelLeemingAvailability
                                + "\n" + "The Warehouse: " + theWarehouseAvailability
                                + "\n" + "JB Hi-Fi: " + jbHifiAvailability
                                + "\n" + "MightyApe: " + mightyApeAvailability;
                        String emailSubject = product.getProductName() + " is Available Online!";
                        EmailUtility.sendEmail(emailSubject, emailText);
                    }
                } else if (noelLeemingAvailability.equals("Unavailable")
                        && theWarehouseAvailability.equals("Unavailable")
                        && jbHifiAvailability.contains("Unavailable")
                        && mightyApeAvailability.equals("On backorder")
                        && (boolean) productEntity.getProperty("online")) {
                    productEntity.setProperty("online", false);
                    DataUtility.updateStock(productEntity);
                }
            }
        }

        if (!stockStatus.isEmpty()){
            DataUtility.updateStock(stockStatus);
        }
    }

}