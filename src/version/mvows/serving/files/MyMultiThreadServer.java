package version.mvows.serving.files;

import version.mvows.weblet.MyWebletConfigs;
import version.mvows.weblet.config.ServerStartup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Konrad on 16.12.13.
 */
public class MyMultiThreadServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);

        new Thread(new SessionDeleter()).start();

        for(String className : MyWebletConfigs.serverStartupClasses){
            try{
                Class clazz = Class.forName(className);
                ServerStartup serverStartupObject = (ServerStartup)clazz.newInstance();
                serverStartupObject.onServerStartup();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for(;;){
            Socket socket = server.accept();
            new ServerInstance(socket).start();
        }
    }
}

