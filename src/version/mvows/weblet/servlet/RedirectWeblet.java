package version.mvows.weblet.servlet;

import version.mvows.weblet.MyWeblet;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Konrad on 04.01.14.
 */
public class RedirectWeblet extends MyWeblet{
    @Override
    public void doRequest(String resource, String queryString, Map<String, String> parameters, PrintWriter out) {
        sendRedirect("fota.JPG");
    }
}
