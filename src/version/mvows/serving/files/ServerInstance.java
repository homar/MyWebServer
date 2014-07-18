package version.mvows.serving.files;

import version.mvows.weblet.MyWebletConfigs;
import version.mvows.weblet.MyWebletProcessor;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;

public class ServerInstance extends Thread{

    private Socket socket;

    public ServerInstance(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            InputStream inputStream = socket.getInputStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader((inputStream)));

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));

            String requestLine = inputReader.readLine();
            System.out.println(requestLine);

            String [] tokens = requestLine.split("\\s+");
            System.out.println(tokens[0]);

            String resource = URLDecoder.decode(tokens[1], "UTF-8");

            String queryString = null;
            int qsIndex = resource.indexOf('?');
            if(qsIndex > 0){
                queryString = resource.substring(qsIndex+1);
                System.out.println("Query String = " + queryString);
                resource = resource.substring(0, qsIndex);
            }

            String cookieHeaderLine = null;

            for(;;){
                String line = inputReader.readLine();

                if(line.startsWith("Cookie:")){
                    cookieHeaderLine = line;
                }else if(line.equals("")){
                    break;
                }
            }

            boolean isServedByWeblet = false;

            for(int i = 0; i < MyWebletConfigs.myWebletConfigs.length; i++){
                String url = MyWebletConfigs.myWebletConfigs[i].url;
                Class clazz = MyWebletConfigs.myWebletConfigs[i].claz;

                if(url.equalsIgnoreCase(resource)){
                   isServedByWeblet = true;
                    MyWebletProcessor mwp = new MyWebletProcessor();
                    mwp.processMyWeblet(clazz, outputWriter, resource, queryString, cookieHeaderLine);
                }
            }
            if(!isServedByWeblet){

                File dir = new File("C:\\MyVeryOwnServerFiles");
                File file = new File(dir, resource);
                if(file.exists()){
                    if(file.isDirectory()){
                        file = new File(file, "index.html");
                    }

                    outputWriter.println("HTTP/1.0 200 OK");
                    outputWriter.println(getContentType(resource));
                    outputWriter.println();
                    outputWriter.flush();

                    FileInputStream in = new FileInputStream(file);
                    int c;

                    while((c = in.read()) >= 0){
                        outputStream.write(c);
                    }
                    in.close();
                }else{
                    outputWriter.println("HTTP/1.0 404 Not Found");
                    outputWriter.println();
                }
            }
            outputWriter.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContentType(String resource){
        if(resource.toLowerCase().endsWith("txt")){
            return "Content-Type: text/plain";
        }else if(resource.toLowerCase().endsWith("html")){
            return "Content-Type: text/html";
        }else if(resource.toLowerCase().endsWith("jpg")){
            return "Content-Type: image/jpeg";
        }else if(resource.toLowerCase().endsWith("wav")){
            return "Content-Type: audio/wav";
        }
        return "Content-Type: text/html";
    }
}
