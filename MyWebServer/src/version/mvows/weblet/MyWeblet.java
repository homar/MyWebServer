package version.mvows.weblet;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Konrad on 24.12.13.
 */
public abstract class MyWeblet {

    public static String SESSION_COOKIE = "mwsessionid";

    static int sessionCookieId = 1;
    static Object sessionCookieLock = new Object();

    static Map<String, MyWebletSession> sessionMap = new ConcurrentHashMap<String, MyWebletSession>();

    protected String contentType;
    private String redirectUrl;
    private Map<String, String> requestCookies;
    private Map<String, String> responseCookies = new HashMap<String, String>();

    public abstract void doRequest(String resource, String queryString, Map<String, String> parameters, PrintWriter out);

    protected void setContentType(String contentType){
        this.contentType = contentType;
    }

    protected void setError(int errorCode, String description){

    }

    protected void sendRedirect(String newUrl){
        redirectUrl = newUrl;
    }

    public String getContentType(){
        if(contentType == null){
            return "text/html";
        }else{
            return contentType;
        }
    }

    public String getRedirectUrl(){
        return redirectUrl;
    }

    public String getRequestCookie(String cookieName){
        return requestCookies.get(cookieName);
    }

    public void setRequestCookies(Map<String, String> requestCookies) {
        this.requestCookies = requestCookies;
    }

    public Map<String, String> getResponseCookies() {
        return responseCookies;
    }

    public void setResponseCookie(String cookieName, String cookieValue) {
        responseCookies.put(cookieName, cookieValue);
    }

    protected MyWebletSession getSession(){
        String sessionCookie = getRequestCookie(SESSION_COOKIE);
        if(sessionCookie == null){
            int id;
            synchronized(sessionCookieLock){
                id = sessionCookieId++;
            }
            sessionCookie = String.valueOf(id);
            setResponseCookie(SESSION_COOKIE, sessionCookie);
        }

        MyWebletSession session = sessionMap.get(sessionCookie);
        if(session == null){
            session = new MyWebletSession();
            sessionMap.put(sessionCookie, session);
        }
        session.setLastUsed(System.currentTimeMillis());
        return session;
    }


    public static Map<String, MyWebletSession> getSessionMap() {
        return sessionMap;
    }


}
