package version.multi.threaded;

import java.io.*;
import java.net.Socket;

/**
 * Created by Konrad on 16.12.13.
 */
public class ClientReadingFromCmd {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 1234);

        OutputStream outputStream = client.getOutputStream();
        PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));

        InputStream inputStream = client.getInputStream();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));

        BufferedReader cmdReader = new BufferedReader(new InputStreamReader(System.in));

        for(;;){
            System.out.println("Command> ");
            String line = cmdReader.readLine();
            outputWriter.println(line);
            outputWriter.flush();
            if(line.equals("exit")){
                break;
            }

            line = inputReader.readLine();
            System.out.println("Server wrote: " + line);
        }
    }
}
