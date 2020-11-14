package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.Product;
import network.NoelLeeming;
import network.TheWarehouse;
import util.DataUtility;


public class test extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        resp.setContentType("text/plain");
        for (Product product: DataUtility.retrieveProduct()){
            resp.getWriter().println(product.getProductName());

            String noelleemingSku = product.getNoelleemingSku();
            NoelLeeming noelLeeming = new NoelLeeming(noelleemingSku);
            resp.getWriter().println("Noel Leeming:" + noelLeeming.checkAvailability());

            String thewarehousePid = product.getThewarehousePid();
            TheWarehouse theWarehouse = new TheWarehouse(thewarehousePid);
            resp.getWriter().println("The Warehouse:" + theWarehouse.checkAvailability());
        }
    }
}
