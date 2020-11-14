package util;

import com.google.appengine.api.datastore.*;

import java.util.List;

public class DataUtility {

    public static void retrieveProduct() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Product");
        List<Entity> products = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        for (Entity product : products) {
            System.out.println(product.toString());
        }
    }
}
