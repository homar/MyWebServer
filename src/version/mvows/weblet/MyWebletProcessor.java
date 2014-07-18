package version.mvows.weblet;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Konrad on 04.01.14.
 */
public class MyWebletProcessor {

    public void processMyWeblet(Class clazz, PrintWriter outputWriter, String resource, String queryString,
                                String cookieHeaderString){
        try{
            Object instance = clazz.newInstance();
            MyWeblet myWeblet = (MyWeblet) instance;

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            PrintWriter out = new PrintWriter(byteArray);

            Map<String, String> parametersMap = parseQueryString(queryString);

            Map<String, String> cookiesMap = parseCookies(cookieHeaderString);
            myWeblet.setRequestCookies(cookiesMap);

            myWeblet.doRequest(resource, queryString, parametersMap, out);

            prepareHeader(myWeblet, outputWriter);

            out.flush();
            outputWriter.println(byteArray.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseCookies(String cookieHeaderString) {
        Map<String, String> result = new HashMap<String, String>();
        if(cookieHeaderString == null){
            return result;
        }
        String lineSubString = cookieHeaderString.substring("Cookie:".length());
        StringTokenizer cookieHeaderTokenizer = new StringTokenizer(lineSubString, ";");
        while(cookieHeaderTokenizer.hasMoreTokens()){
            String[] cookieTokens = cookieHeaderTokenizer.nextToken().split("=");
            if(cookieTokens.length == 2){
                String cookieName = cookieTokens[0].trim();
                String cookieValue = cookieTokens[1].trim();
                result.put(cookieName, cookieValue);
            }
        }
        return result;
    }

    private Map<String, String> parseQueryString(String queryString) throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<String, String>();
        if(queryString == null){
            return result;
        }

        StringTokenizer qTokens = new StringTokenizer(queryString, "&");
        while(qTokens.hasMoreTokens()){
            String[] pTokens = qTokens.nextToken().split("=");
            if(pTokens.length == 2){
                String parameterName = pTokens[0];
                String parameterValue = pTokens[1];

                result.put(parameterName, parameterValue);
            }
        }
        return result;
    }

    private void prepareHeader(MyWeblet myWeblet, PrintWriter outputWriter) {
        if(myWeblet.getRedirectUrl() != null){
            printStatus(myWeblet, outputWriter);
        }else{
            outputWriter.println("HTTP/1.0 200 OK");
            printContentType(myWeblet, outputWriter);
        }
        printCookies(myWeblet,outputWriter);
        outputWriter.println();
    }

    private void printCookies(MyWeblet myWeblet, PrintWriter outputWriter) {
        if(myWeblet.getResponseCookies().size() != 0){
            for(String name : myWeblet.getResponseCookies().keySet()){
                StringBuilder cookieString = new StringBuilder("Set-Cookie: ");
                cookieString.append(name).append("=").append(myWeblet.getResponseCookies().get(name)).append("; ");
                cookieString.append("Path=/");
                outputWriter.println(cookieString);
            }
        }
    }

    private void printStatus(MyWeblet myWeblet, PrintWriter outputWriter){
        outputWriter.println("HTTP/1.0 302 Found");
        outputWriter.println("Location: " + myWeblet.getRedirectUrl());
    }

    private void printContentType(MyWeblet myWeblet, PrintWriter outputWriter){
        String contentType = "Content-Type:" + myWeblet.getContentType();
        outputWriter.println(contentType);
    }

}
