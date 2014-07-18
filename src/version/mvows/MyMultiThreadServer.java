package version.mvows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Konrad on 16.12.13.
 */
public class MyMultiThreadServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);

        for(;;){
            Socket socket = server.accept();
            new ServerInstance(socket).start();
        }
    }
}

