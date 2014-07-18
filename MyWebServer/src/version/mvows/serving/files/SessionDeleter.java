package version.mvows.serving.files;

import version.mvows.weblet.MyWeblet;
import version.mvows.weblet.MyWebletSession;

import java.util.Set;

/**
 * Created by Konrad on 05.01.14.
 */
public class SessionDeleter implements Runnable{
    @Override
    public void run() {
        for(;;){
            try{
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long expirationTime = System.currentTimeMillis() - 120000;

            for(String key : MyWeblet.getSessionMap().keySet()){
                MyWebletSession session = MyWeblet.getSessionMap().get(key);

                if(session.getLastUsed() < expirationTime){
                    System.out.println("Deleteting session " + key);
                    MyWeblet.getSessionMap().remove(key);
                }
            }
        }
    }
}
