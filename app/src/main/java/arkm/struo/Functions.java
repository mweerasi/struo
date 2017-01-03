package arkm.struo;

/**
 * Created by manju on 2017-01-03.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Functions {
    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "168b9d1fbc6ecad777c0ee9511dde039";

    public static String setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime < sunset){
                icon = "&#xf00d;";
            } else {
                switch(id){
                    case 2 : icon = "&#xf01e;";
                        break;
                    case 3 : icon = "&#xf01c;";
                        break;
                    case 7 : icon = "&#xf014;";
                        break;
                    case 8 : icon = "&#xf013;";
                        break;
                    case 6 : icon = "&#xf01b;";
                        break;
                    case 5 : icon = "&#xf019;";
                        break;
                }
            }
            return icon;
        }
    }

}
