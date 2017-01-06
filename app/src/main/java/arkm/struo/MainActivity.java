package arkm.struo;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

//weather imports

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class MainActivity extends AppCompatActivity {

    public OpenWeatherMap weatherObject;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private CalendarFragment calendarFragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set weather city
        fetchWeatherAsync("Waterloo", "ca");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    // TODO: Move fragments to their own separate files
    public static class ChecklistFragment extends Fragment {
        public ChecklistFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.checklist_main, container, false);
        }
    }

    public static class MindMapFragment extends Fragment {
        public MindMapFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.mindmap_main, container, false);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    calendarFragment = new CalendarFragment();
                    return calendarFragment;
                case 1:
                    return new ChecklistFragment();
                case 2:
                    return new MindMapFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Calendar";
                case 1:
                    return "Checklist";
                case 2:
                    return "Mind Map";
            }
            return null;
        }
    }

    public void Weather(String weatherJSON) {

        Gson gson = new Gson();
        OpenWeatherMap weatherObject = gson.fromJson(weatherJSON, OpenWeatherMap.class);

        calendarFragment.setCityField(weatherObject);

    }

    public static class OpenWeatherMap{
        @SerializedName("coord")
        public Coord coord;
        @SerializedName("sys")
        Sys sys;
        @SerializedName("weather")
        Weather[] weather;
        @SerializedName("main")
        Main main;
//        @SerializedName("clouds")
//        String clouds;
        @SerializedName("name")
        String name;
        @SerializedName("wind")
        Wind wind;



    }
    public class Coord{
        @SerializedName("lon")
        double lon;
        @SerializedName("lat")
        double lat;
    }
    public class Sys{
        @SerializedName("country")
        String country;
        @SerializedName("sunrise")
        int sunride;
        @SerializedName("sunset")
        int sunset;
    }
    public class Weather{
        @SerializedName("main")
        String main;
        @SerializedName("description")
        String description;
    }
    public class Main{
        @SerializedName("temp")
        double temp;
        @SerializedName("humidity")
        int humidity;
        @SerializedName("pressure")
        double pressure;
        @SerializedName("temp_min")
        double minTemp;
        @SerializedName("temp_max")
        double maxTemp;
    }
    public class Wind{
        @SerializedName("speed")
        double speed;
        @SerializedName("deg")
        double deg;
    }

    public void fetchWeatherAsync(String city, String countryCode) {//doing it by ZIP code
//        String baseUrl = "api.openweathermap.org/data/2.5/weather";
//        String url = String.format("%s?q=%s,%s&appid=%s", baseUrl, city, countryCode,
//                getResources().getString(R.string.weather_api_key));
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Waterloo,ca&appid=168b9d1fbc6ecad777c0ee9511dde039";

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest weatherCall = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response is the json string
                        /*
                        {
                        "coord":{"lon":-122.09,"lat":37.39},
                        "sys":{"type":3,"id":168940,"message":0.0297,"country":"US","sunrise":1427723751,"sunset":1427768967},
                        "weather":[{"id":800,"main":"Clear","description":"Sky is Clear","icon":"01n"}],
                        "base":"stations",
                        "main":{"temp":285.68,"humidity":74,"pressure":1016.8,"temp_min":284.82,"temp_max":286.48},
                        "wind":{"speed":0.96,"deg":285.001},
                        "clouds":{"all":0},
                        "dt":1427700245,
                        "id":0,
                        "name":"Mountain View",
                        "cod":200
                        }
                         */
                        Weather(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.getLocalizedMessage());
                    }
                });

        queue.add(weatherCall);
    }

}
