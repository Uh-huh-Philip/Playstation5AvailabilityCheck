package util;

import com.google.appengine.api.datastore.*;
import model.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataUtility {

    public static ArrayList<Product> retrieveProduct() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Product");
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        List<Entity> products = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        for (Entity product : products) {
            productArrayList.add(new Product(product.getProperty("productName").toString(),
                    product.getProperty("thewarehousePid").toString(),
                    product.getProperty("noelleemingSku").toString(),
                    product.getProperty("jbhifiId").toString(),
                    product.getProperty("mightyApeUrl").toString()));
        }
        return productArrayList;
    }

    public static void putData(String productName, String retailer, String status) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastore.beginTransaction();
        Date date = Calendar.getInstance().getTime();
        Entity availability = new Entity("availability");
        availability.setProperty("productName", productName);
        availability.setProperty("retailer", retailer);
        availability.setProperty("status", status);
        availability.setProperty("time", date);
        datastore.put(txn, availability);
        txn.commit();
    }
}
