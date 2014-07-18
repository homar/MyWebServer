package version.mvows.weblet.servlet;

import version.mvows.weblet.MyWeblet;
import version.mvows.weblet.MyWebletSession;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by Konrad on 04.01.14.
 */
public class FirstAndLastNameWeblet extends MyWeblet{
    static int cookieNumber = 1;
    static Object cookieLock = new Object();

    @Override
    public void doRequest(String resource, String queryString, Map<String, String> parameters, PrintWriter out) {

        String firstName = parameters.get("firstname");
        String lastName = parameters.get("lastname");

        if(firstName == null || lastName == null){
            sendRedirect("EnterName.html");
        }else{
            MyWebletSession session = getSession();
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);
            sendRedirect("SessionTest");
        }
    }
}
