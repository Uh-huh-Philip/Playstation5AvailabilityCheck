package servlet;

import network.TheWarehouse;
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
        String thewarehousePid = "R2695124";
        TheWarehouse theWarehouse = new TheWarehouse(thewarehousePid, userAgent);
        resp.getWriter().println("The Warehouse: " + theWarehouse.checkAvailability());

        //EmailUtility.sendEmail();
    }
}