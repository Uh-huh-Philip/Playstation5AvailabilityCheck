package servlet;

import network.MightyApe;
import util.EmailUtility;
import util.RandomUserAgent;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class test extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String userAgent = RandomUserAgent.getRandomUserAgent();
        resp.setContentType("text/plain");
        String thewarehousePid = "R2695124";
        String mightyApeUrl = "https://www.mightyape.co.nz/product/cyberpunk-2077-day-one-edition-ps4/28143246";
        MightyApe mightyApe = new MightyApe(mightyApeUrl, userAgent);
        resp.getWriter().println("MightyApe: " + mightyApe.checkAvailability());

        EmailUtility.sendEmail("MightyApe: " + mightyApe.checkAvailability(),"");
    }
}
