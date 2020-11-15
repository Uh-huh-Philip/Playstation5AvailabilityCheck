package util;

import com.google.appengine.api.datastore.*;
import com.sun.istack.internal.Nullable;
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

    public static void updateStock(List<Entity> stockStatus) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //Transaction txn = datastore.beginTransaction();
//        Date date = Calendar.getInstance().getTime();
//        Entity stockStatus;
//        if (parent != null)
//            stockStatus = new Entity("StockStatus",parent);
//        else
//            stockStatus = new Entity("StockStatus");
//        stockStatus.setProperty("retailer", retailer);
//        stockStatus.setProperty("status", status);
//        stockStatus.setProperty("time", date);
        //datastore.put(txn, stockStatus);
        //txn.commit();
        datastore.put(stockStatus);
    }


    public static Entity stockStatusBuilder(@Nullable Key parent, String retailer, String status){
        Date date = Calendar.getInstance().getTime();
        Entity stockStatus;
        if (parent != null)
            stockStatus = new Entity("StockStatus",parent);
        else
            stockStatus = new Entity("StockStatus");
        stockStatus.setProperty("retailer", retailer);
        stockStatus.setProperty("status", status);
        stockStatus.setProperty("time", date);
        return stockStatus;
    }
}
