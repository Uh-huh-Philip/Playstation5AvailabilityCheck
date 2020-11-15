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

public class update extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String userAgent = RandomUserAgent.getRandomUserAgent();

        resp.setContentType("text/plain");
        for (Entity productEntity: DataUtility.retrieveProduct()){
            Product product = new Product(productEntity.getProperty("productName").toString(),
                    productEntity.getProperty("thewarehousePid").toString(),
                    productEntity.getProperty("noelleemingSku").toString(),
                    productEntity.getProperty("jbhifiId").toString(),
                    productEntity.getProperty("mightyApeUrl").toString());

            resp.getWriter().println(product.getProductName());

            String noelleemingSku = product.getNoelleemingSku();
            NoelLeeming noelLeeming = new NoelLeeming(noelleemingSku);
            String noelLeemingAvailability = noelLeeming.checkAvailability();
            DataUtility.updateStock(productEntity.getKey(),"Noel Leeming", noelLeemingAvailability);
            resp.getWriter().println("Noel Leeming: " + noelLeemingAvailability);

            String thewarehousePid = product.getThewarehousePid();
            TheWarehouse theWarehouse = new TheWarehouse(thewarehousePid, userAgent);
            String theWarehouseAvailability = theWarehouse.checkAvailability();
            DataUtility.updateStock(productEntity.getKey(),"The Warehouse", theWarehouseAvailability);
            resp.getWriter().println("The Warehouse: " + theWarehouseAvailability);

            String jbhifiId = product.getJbhifiId();
            JbHifi jbHifi = new JbHifi(jbhifiId);
            String jbHifiAvailability = jbHifi.checkAvailability();
            DataUtility.updateStock(productEntity.getKey(),"JB Hi-Fi", jbHifiAvailability);
            resp.getWriter().println("JB Hi-Fi: " + jbHifiAvailability);

            MightyApe mightyApe = new MightyApe(product.getMightyApeUrl(), userAgent);
            String mightyApeAvailability = mightyApe.checkAvailability();
            DataUtility.updateStock(productEntity.getKey(),"MightyApe", mightyApeAvailability);
            resp.getWriter().println("MightyApe: " + mightyApeAvailability);

            if (noelLeemingAvailability.equals("Available")
                    ||noelLeemingAvailability.equals("Preorder available")
                    ||theWarehouseAvailability.equals("Available")
                    ||theWarehouseAvailability.equals("Preorder available")
                    ||jbHifiAvailability.contains("Available")
                    ||jbHifiAvailability.contains("Preorder available")
                    ||mightyApeAvailability.equals("Available")
                    ||mightyApeAvailability.equals("Preorder available")
                    ||mightyApeAvailability.equals("On backorder")){
                String emailText = "Noel Leeming: " + noelLeemingAvailability
                        + "\n" + "The Warehouse: " + theWarehouseAvailability
                        + "\n" + "JB Hi-Fi: " + jbHifiAvailability;
                String emailSubject = product.getProductName() + " is Available Online!";
                EmailUtility.sendEmail(emailSubject,emailText);
            }
        }
    }
}