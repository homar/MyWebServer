package version.mvows;

import java.io.*;
import java.net.Socket;

public class ServerInstance extends Thread{

    private Socket socket;

    private static int cookieNumber = 1;

    public ServerInstance(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            InputStream inputStream = socket.getInputStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader((inputStream)));

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));

            boolean haveCookie = false;

            for(;;){
                String line = inputReader.readLine();
                System.out.println(line);
                if(line.equals("")){
                    break;
                }
                if(line.startsWith("Cookie: ")){
                    System.out.println("** Cookie Line **");
                    haveCookie = true;
                    System.out.println(line);
                }
            }
            outputWriter.println("HTTP/1.0 200 OK");
            outputWriter.println("Content-Type: text/html");
            if(!haveCookie){
                String cookieValue = "cookie_" + cookieNumber++;
                String cookieName = "MyVeryOwnCookie1";
                String cline = "Set-Cookie: " + cookieName + ":" + cookieValue
                        + "; Expires=Wed,18 Dec 2013 10:18:14 GMT";
                outputWriter.println(cline);
                System.out.println("Sending cookie: " + cline);
            }
            outputWriter.println();
            outputWriter.println("<HTML>");
            outputWriter.println("<HEAD>");
            outputWriter.println("<LINK rel=\"shortcut icon\" href=\"about:bland\"/>");
            outputWriter.println("</HEAD>");
            outputWriter.println("<BODY>");
            outputWriter.println("Hello from ");
            outputWriter.println("My Very Own ");
            outputWriter.println("Web Server");
            outputWriter.println("</BODY>");
            outputWriter.println("</HTML>");
            outputWriter.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
