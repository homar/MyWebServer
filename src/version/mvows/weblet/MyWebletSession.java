package version.mvows.weblet;


import com.sun.javafx.collections.MappingChange;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konrad on 05.01.14.
 */
public class MyWebletSession {
    Map<String, Object> data = new HashMap<String, Object>();
    long lastUsed = System.currentTimeMillis();

    public void setAttribute(String key, Object value){
        data.put(key, value);
    }

    public Object getAttribute(String key){
        return data.get(key);
    }

    public void setLastUsed(long currentTime){
        lastUsed = currentTime;
    }

    public long getLastUsed() {
        return lastUsed;
    }
}
