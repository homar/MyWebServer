package version.simple;

import java.io.*;
import java.net.Socket;

/**
 * Created by Konrad on 16.12.13.
 */
public class MySocketClient {
    public static void main(String[] args){
        Socket client = null;
        try {
            client = new Socket("localhost", 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null;
        try {
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStreamWriter oswriter = new OutputStreamWriter(outputStream);
        PrintWriter outputWriter = new PrintWriter(oswriter);
        outputWriter.println("Hello from client");
        outputWriter.flush();

        InputStream inputStream = null;
        try {
            inputStream = client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            System.out.println("Server wrote: " + inputReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
