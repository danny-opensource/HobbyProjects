package com.weatherapp.activities;

import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.survivingwithandroid.weatherapp.R;
import com.weatherapp.model.WeatherModel;
import com.weatherapp.utilities.WeatherDataFetcher;
import com.weatherapp.utilities.WeatherDataParser;

/**
 * The main activity for the YourWeather App. This is the entry activity for the
 * application. This defines the real-estate for the weather app
 * 
 */
public class MainActivity extends Activity {

	private TextView textCityVal;
	private TextView txtConditionDesc;
	private TextView txtTemperature;
	private TextView txtPressure;
	private TextView txtWindSpeed;
	private TextView txtWindDegree;
	private TextView txtHumidity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textCityVal = (TextView) findViewById(R.id.cityText);
		txtConditionDesc = (TextView) findViewById(R.id.condDescr);
		txtTemperature = (TextView) findViewById(R.id.temp);
		txtHumidity = (TextView) findViewById(R.id.valHumidity);
		txtPressure = (TextView) findViewById(R.id.valPressure);
		txtWindSpeed = (TextView) findViewById(R.id.valWind);
		txtWindDegree = (TextView) findViewById(R.id.valWindDegree);
		Button getWeather = (Button) findViewById(R.id.getWeather);
		String defaultCity = "Chicago";
		final EditText weatherContent = (EditText) findViewById(R.id.enterCity);

		WeatherFetcherTask task = new WeatherFetcherTask();
		task.execute(new String[] { defaultCity });

		getWeather.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WeatherFetcherTask task = new WeatherFetcherTask();
				task.execute(new String[] { weatherContent.getText().toString() });
			}
		});

	}

	/**
	 * WeatherFetcherTask spawns a thread and talks to the weather fetcher
	 * utility and gets the weather data
	 * 
	 */
	private class WeatherFetcherTask extends AsyncTask<String, Void, WeatherModel> {

		@Override
		protected WeatherModel doInBackground(String... params) {
			WeatherModel weather = new WeatherModel();
			String data = ((new WeatherDataFetcher()).fetchWeatherDataFromURL(params[0]));

			try {
				weather = WeatherDataParser.getWeatherInfoFromJson(data);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return weather;

		}

		@Override
		protected void onPostExecute(WeatherModel weather) {
			super.onPostExecute(weather);

			textCityVal.setText(weather.location.getCity() + "," + weather.location.getCountry());
			txtConditionDesc.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
			txtTemperature.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "\u00b0" + "C");
			txtHumidity.setText("" + weather.currentCondition.getHumidity() + "%");
			txtPressure.setText("" + weather.currentCondition.getPressure() + " hPa");
			txtWindSpeed.setText("" + weather.wind.getSpeed() + " mps");
			txtWindDegree.setText("" + weather.wind.getDeg() + "\u00b0");

		}

	}
}
