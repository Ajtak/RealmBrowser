package eu.jafr.realmbrowser.library;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

/**
 * @author Jakub Fridrich (http://fb.com/xfridrich)
 * @since 5.3.2017
 */


public class RealmBrowser {

    public static final String TAG = "RealmBrowser";

    private static final int DEFAULT_PORT = 8488;
    private RealmBrowserHTTPD server;

    public RealmBrowser() {
    }

    public void start() {
        start(DEFAULT_PORT);
    }

    public void start(int port) {
        try {
            if (server == null) {
                server = new RealmBrowserHTTPD(port);
            }
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }
    }

    public void showServerAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format(Locale.ROOT, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        Log.d(TAG, "RealmBrowser adress: http://" + formatedIpAddress + ":" + server.getListeningPort());
    }

    public String getServerAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String formatedIpAddress = String.format(Locale.ROOT, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return String.format(Locale.ROOT, "%s:%d", formatedIpAddress, server.getListeningPort());
    }
}
