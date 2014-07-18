package version.multi.threaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Konrad on 16.12.13.
 */
public class MyMultiThreadServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);

        for(;;){
            Socket socket = server.accept();
            new ServerInstance(socket).start();
        }
    }
}

class ServerInstance extends Thread{

    private Socket socket;
    private boolean terminate;

    public ServerInstance(Socket socket){
        this.socket = socket;
        terminate = false;
    }

    public void run(){
        try{
            InputStream inputStream = socket.getInputStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader((inputStream)));

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));

            while(!terminate){
                process(inputReader, outputWriter);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void process(BufferedReader inputReader, PrintWriter outputWriter) throws IOException {
        String msg = inputReader.readLine();
        outputWriter.println("Echo " + msg);
        outputWriter.flush();
        if(msg.equals("exit")){
            terminate = true;
            System.out.println("Connection terminated");
        }
    }

}
