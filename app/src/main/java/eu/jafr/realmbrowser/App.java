package eu.jafr.realmbrowser;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @package eu.jafr.realmbrowser
 * @since 5.3.2017
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

    }
}
