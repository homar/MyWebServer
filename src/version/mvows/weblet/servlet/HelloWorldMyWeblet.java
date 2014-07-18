package version.mvows.weblet.servlet;

import version.mvows.weblet.MyWeblet;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Konrad on 04.01.14.
 */
public class HelloWorldMyWeblet extends MyWeblet{

    @Override
    public void doRequest(String resource, String queryString, Map<String, String> parameters, PrintWriter out) {
        setContentType("text/html");
        out.println("<HTML>");
        out.println("<BODY>");
        out.println("<H2>Hello, World</H2>");
        out.println("Hello from My First MyWeblet");
        out.println("</BODY>");
        out.println("</HTML>");
    }

}
