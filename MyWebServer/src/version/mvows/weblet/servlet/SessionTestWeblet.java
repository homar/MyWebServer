package version.mvows.weblet.servlet;

import version.mvows.weblet.MyWeblet;
import version.mvows.weblet.MyWebletSession;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by Konrad on 05.01.14.
 */
public class SessionTestWeblet extends MyWeblet {
    @Override
    public void doRequest(String resource, String queryString, Map<String, String> parameters, PrintWriter out) {
        MyWebletSession session = getSession();
        String firstName = (String)session.getAttribute("firstName");
        String lastName = (String)session.getAttribute("lastName");
        if(firstName == null || lastName == null){
            sendRedirect("EnterName.html");
        }else{
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("Hello " + firstName + " " + lastName);
            out.println("</BODY>");
            out.println("</HTML>");
        }
    }
}
