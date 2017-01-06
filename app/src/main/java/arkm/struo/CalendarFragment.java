package arkm.struo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by manju on 2017-01-04.
 */
public class CalendarFragment extends Fragment {

    private TextView cityField, updatedField, detailsField, currentTemperatureField, weatherIcon, windSpeed;
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

        //MainActivity.fetchWeatherAsync("Waterloo", "ca");

        //for weather

        cityField = (TextView) view.findViewById(R.id.cityField);
        updatedField = (TextView) view.findViewById(R.id.updatedField);
        detailsField = (TextView) view.findViewById(R.id.detailsField);
        currentTemperatureField = (TextView) view.findViewById(R.id.currentTemperatureField);
        weatherIcon = (TextView) view.findViewById(R.id.weatherIcon);
        windSpeed = (TextView) view.findViewById(R.id.windSpeed);

        Typeface weatherFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

//            updatedField.setText(weather_updatedOn);
//            detailsField.setText(weather_description);
//            humidity_field.setText("Humidity: "+weather_humidity);
//            pressure_field.setText("Pressure: "+weather_pressure);
//            weatherIcon.setText(Html.fromHtml(weather_iconText));

        return view;
    }

    public void setCityField(MainActivity.OpenWeatherMap weatherObject){
        String windDeg;
        cityField.setText(weatherObject.name + ", " + weatherObject.sys.country);

        detailsField.setText(weatherObject.weather[0].description);
        currentTemperatureField.setText(String.format("%.2f"+ (char) 0x00B0 +"C", weatherObject.main.temp - 273.15));

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
    }
}
