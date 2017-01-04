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


        //for weather
        TextView cityField = (TextView) view.findViewById(R.id.city_field);
        TextView updatedField = (TextView) view.findViewById(R.id.updated_field);
        TextView detailsField = (TextView) view.findViewById(R.id.details_field);
        TextView currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        TextView weatherIcon = (TextView) view.findViewById(R.id.weather_icon);

        Typeface weatherFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

        fetchWeatherAsync("Waterloo", "ca");
        cityField.setText(MainActivity.OpenWeatherMap.name.city);
//            updatedField.setText(weather_updatedOn);
//            detailsField.setText(weather_description);
//            currentTemperatureField.setText(weather_temperature);
//            humidity_field.setText("Humidity: "+weather_humidity);
//            pressure_field.setText("Pressure: "+weather_pressure);
//            weatherIcon.setText(Html.fromHtml(weather_iconText));

        return view;
    }
}
