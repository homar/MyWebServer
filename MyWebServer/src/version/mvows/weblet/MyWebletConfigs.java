package version.mvows.weblet;

import version.mvows.weblet.servlet.FirstAndLastNameWeblet;
import version.mvows.weblet.servlet.HelloWorldMyWeblet;
import version.mvows.weblet.servlet.RedirectWeblet;
import version.mvows.weblet.servlet.SessionTestWeblet;

/**
 * Created by Konrad on 04.01.14.
 */
public class MyWebletConfigs {
    public String url;
    public Class claz;

    public MyWebletConfigs(String url, Class claz){
        this.url = url;
        this.claz = claz;
    }

    public static MyWebletConfigs[] myWebletConfigs = new MyWebletConfigs[]{
        new MyWebletConfigs("/HelloWorld", HelloWorldMyWeblet.class),
        new MyWebletConfigs("/RedirectWeblet", RedirectWeblet.class),
        new MyWebletConfigs("/ProcessName", FirstAndLastNameWeblet.class),
        new MyWebletConfigs("/SessionTest", SessionTestWeblet.class)
    };

    public static String[] serverStartupClasses = {
      "version.mvows.weblet.MyServerStartup"
    };
}
