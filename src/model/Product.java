package model;

import java.net.URL;

public class Product {
    private final String productName;
    private final String thewarehousePid;
    private final String noelleemingSku;
    private final String jbhifiId;
    private final URL mightyApeUrl;

    public Product(String productName, String thewarehousePid, String noelleemingSku, String jbhifiId, URL mightyApeUrl) {
        this.productName = productName;
        this.thewarehousePid = thewarehousePid;
        this.noelleemingSku = noelleemingSku;
        this.jbhifiId = jbhifiId;
        this.mightyApeUrl = mightyApeUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getThewarehousePid() {
        return thewarehousePid;
    }

    public String getNoelleemingSku() {
        return noelleemingSku;
    }

    public String getJbhifiId() {
        return jbhifiId;
    }

    public URL getMightyApeUrl() {
        return mightyApeUrl;
    }
}
