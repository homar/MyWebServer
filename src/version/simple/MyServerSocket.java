package version.simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Konrad on 16.12.13.
 */
public class MyServerSocket{

    public static void main(String[] args){
        ServerSocket server = null;
        try {
            server = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server created, waiting for client");

        Socket socket = null;
        try {
             socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client has connected");
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader inputReader = new BufferedReader(isReader);

        try {
            System.out.println("Client wrote: " + inputReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        outputWriter.println("Hello from server");
        outputWriter.flush();
    }


}
