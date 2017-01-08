package arkm.struo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by manju on 2017-01-04.
 */
public class CalendarFragment extends Fragment {

    private TextView cityField, updatedField, detailsField, currentTemperatureField, weatherIcon, windSpeed; //Weather textviews
    private Button updateField;
    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_main, container, false);
        TextView yearView = (TextView) view.findViewById(R.id.yearView);
        TextView monthView = (TextView) view.findViewById(R.id.monthView);
        TextView dayNumView = (TextView) view.findViewById(R.id.dayNumberView);
        TextView date = (TextView) view.findViewById(R.id.date);

        //create calendar instance
        Calendar cal = Calendar.getInstance();
        //output text onto calendar view
        yearView.setText(new SimpleDateFormat("yyyy", Locale.CANADA).format(cal.getTime()));
        monthView.setText(new SimpleDateFormat("MMMM", Locale.CANADA).format(cal.getTime()));
        dayNumView.setText(new SimpleDateFormat("d", Locale.CANADA).format(cal.getTime()));
        date.setText(new SimpleDateFormat("E", Locale.CANADA).format(cal.getTime()));

        ((MainActivity)getActivity()).fetchWeatherAsync("Waterloo", "ca"); //default for now

        //for weather

        cityField = (TextView) view.findViewById(R.id.cityField);
        //updatedField = (TextView) view.findViewById(R.id.updatedField);
        detailsField = (TextView) view.findViewById(R.id.detailsField);
        currentTemperatureField = (TextView) view.findViewById(R.id.currentTemperatureField);
        weatherIcon = (TextView) view.findViewById(R.id.weatherIcon);
        windSpeed = (TextView) view.findViewById(R.id.windSpeed);

        updatedField = (Button) view.findViewById(R.id.updateField);

        cityField.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        Typeface weatherFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

//            humidity_field.setText("Humidity: "+weather_humidity);
//            pressure_field.setText("Pressure: "+weather_pressure);

        return view;
    }

    private void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from((MainActivity)getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((MainActivity)getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editCity = (EditText) promptView.findViewById(R.id.editCity);
        final EditText editCountry = (EditText) promptView.findViewById(R.id.editCountry);
        // setup a dialog window
        // TODO: Have input dialog show current city/country
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity)getActivity()).fetchWeatherAsync(editCity.getText().toString(), editCountry.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alert.show();
    }

    public void setWeatherField(MainActivity.OpenWeatherMap weatherObject){
        String windDeg;
        //output the weather fields
        cityField.setText(weatherObject.name + ", " + weatherObject.sys.country);
        detailsField.setText(weatherObject.weather[0].description);
        currentTemperatureField.setText(String.format("%.2f"+ (char) 0x00B0 +"C", weatherObject.main.temp - 273.15));

        //setting the compass directions based on the degrees
        if (weatherObject.wind.deg > 337.5 && weatherObject.wind.deg <= 22.5){
            windDeg = String.format("N");
        } else if (weatherObject.wind.deg > 22.5 && weatherObject.wind.deg <= 67.5){
            windDeg = String.format("NE");
        } else if (weatherObject.wind.deg > 67.5 && weatherObject.wind.deg <= 112.5){
            windDeg = String.format("E");
        } else if (weatherObject.wind.deg > 112.5 && weatherObject.wind.deg <= 157.5){
            windDeg = String.format("SE");
        } else if (weatherObject.wind.deg > 157.5 && weatherObject.wind.deg <= 202.5){
            windDeg = String.format("S");
        } else if (weatherObject.wind.deg > 202.5 && weatherObject.wind.deg <= 247.5){
            windDeg = String.format("SW");
        } else if (weatherObject.wind.deg > 247.5 && weatherObject.wind.deg <= 292.5){
            windDeg = String.format("W");
        } else {
            windDeg = String.format("NW");
        }
        windSpeed.setText(String.format("%.3f km/h %s", weatherObject.wind.speed*3600/1000, windDeg));

        weatherIcon.setText(Html.fromHtml(setWeatherIcon(weatherObject.weather[0].id, weatherObject.sys.sunrise * 1000, weatherObject.sys.sunset * 1000)));

    }

    public static String setWeatherIcon(int actualId, long sunrise, long sunset){
        //setting the icons based on the Id provided by OWM
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch(id) {
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
