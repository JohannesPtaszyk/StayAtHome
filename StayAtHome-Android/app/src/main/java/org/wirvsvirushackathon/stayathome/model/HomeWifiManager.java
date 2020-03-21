package org.wirvsvirushackathon.stayathome.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.security.MessageDigest;
import java.util.Objects;

import static android.content.Context.WIFI_SERVICE;

public class HomeWifiManager {

    private static final String KEY_HOME_WIFI_HASH = "KEY_HOME_WIFI_HASH";
    private static final String DEBUG_TAG = "WifiIdentificationServ";

    private SharedPreferences preferences;
    private ConnectivityManager connMgr;
    private WifiManager wifiManager;


    public HomeWifiManager(Context context) {
        Context appContext = context.getApplicationContext();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        this.connMgr = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.wifiManager = (WifiManager) appContext.getSystemService(WIFI_SERVICE);
    }

    public boolean isConnectedToWifi() {
        //Schauen ob wlan eingeschaltet ist und eine Verbindung besteht
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo == null) {
                continue;
            }

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return networkInfo.isConnected();
            }
        }

        return false;
    }

    private String getCurrentWifiSSIDHash() {
        String resultHash = null;

        if (isConnectedToWifi()) {
            WifiInfo info = wifiManager.getConnectionInfo();
            String currentSSiD = info.getSSID();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(
                        currentSSiD.getBytes());
                resultHash = toHexString(hash);
            } catch (Exception ex) {
                Log.d(DEBUG_TAG,
                      "getCurrentWifiSSIDHash: Aktueller Wifi Hash konnte "
                              + "nicht erzeugt werden: "
                              + ex.getMessage());
                return null;
            }
        } else {
            Log.d(DEBUG_TAG,
                  "getCurrentWifiSSIDHash: Aktueller Wifi Hash konnte nicht "
                          + "erzeugt werden: WLAN nicht verbunden");
        }

        return resultHash;
    }

    public boolean setHomeWifiFromCurrentWifi() {
        if (isConnectedToWifi()) {
            //Hash erstellen
            String currentWifiHash = getCurrentWifiSSIDHash();

            //Hash in shared preferences speichern
            preferences.edit()
                       .putString(KEY_HOME_WIFI_HASH, currentWifiHash)
                       .apply();
            Log.d(DEBUG_TAG,
                  "setHomeWifiFromCurrentWifi: Hash gespeichert: "
                          + currentWifiHash);
            return true;
        }
        Log.d(DEBUG_TAG, "setHomeWifiFromCurrentWifi: Keine WLAN Verbindung");
        return false;
    }

    //Hilfsmethode
    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public boolean isConnectedToHomeWifi() {
        if (isConnectedToWifi()) {
            //Wifi auslesen und hash erstellen
            String wifiSSIDHashCurrent = getCurrentWifiSSIDHash();

            //Hash mit Wert aus shared preferences vergleichen
            String
                    wifiSSIDHashStored
                    = preferences.getString(KEY_HOME_WIFI_HASH, "");
            Log.d(DEBUG_TAG,
                  "isConnectedToHomeWifi: Hashwert geladen: "
                          + wifiSSIDHashStored);

            if (Objects.equals(wifiSSIDHashCurrent, wifiSSIDHashStored)) {
                //WLAN von zuhause verbunden
                Log.d(DEBUG_TAG, "isConnectedToHomeWifi: Home Wifi verbunden");
                return true;
            } else {
                //WLAN von zuhause nicht verbunden (anderes WLAN)
                Log.d(DEBUG_TAG,
                      "isConnectedToHomeWifi: Anderes Wifi verbunden");
                return false;
            }
        }
        Log.d(DEBUG_TAG, "isConnectedToHomeWifi: Keine WLAN Verbindung");
        return false;
    }
}
