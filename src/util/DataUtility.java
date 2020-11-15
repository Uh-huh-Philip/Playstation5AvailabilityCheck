package util;

import com.google.appengine.api.datastore.*;
import model.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataUtility {

    public static List<Entity> retrieveProduct() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Product");
        List<Entity> productEntityList = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        for (Entity product : productEntityList) {
            productArrayList.add(new Product(product.getProperty("productName").toString(),
                    product.getProperty("thewarehousePid").toString(),
                    product.getProperty("noelleemingSku").toString(),
                    product.getProperty("jbhifiId").toString(),
                    product.getProperty("mightyApeUrl").toString()));
        }
        return productEntityList;
    }

    public static void updateStock(Key parent, String retailer, String status) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastore.beginTransaction();
        Date date = Calendar.getInstance().getTime();
        Entity stockStatus = new Entity("StockStatus",parent);
        stockStatus.setProperty("retailer", retailer);
        stockStatus.setProperty("status", status);
        stockStatus.setProperty("time", date);
        datastore.put(txn, stockStatus);
        txn.commit();
    }
}
