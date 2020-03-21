package org.wirvsvirushackathon.stayathome.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class WifiIdentificationService  extends Service {

    public static boolean isConnectedToWifi() {
        //Schauen ob wlan eingeschaltet ist und eine Verbindung besteht
        return true;
    }

    private static String getCurrentWifiSSIDHash(String ssid) {
        //Hash erstellen

        return "";
    }

    public static boolean isConnectedToHomeWifi() {
        //TODO
        if (isConnectedToWifi()) {
            //Wifi auslesen und hash erstellen

            //Hash mit Wert aus shared preferences  vergleichen
        }
        return false;

    }

    public static boolean setHomeWifiFromCurrentWifi() {
        if (isConnectedToWifi()) {
            //Hash erstellen

            //Hash in shared preferences speichern

            return true;
        }

        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
