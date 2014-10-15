package com.weatherapp.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.weatherapp.constants.URLConstants;

/**
 * Utility class for performing important weather data operations such as
 * fetching weather data and parsing into json to name a few. This class can be
 * expanded in the future to add more utilities based on requirement
 * 
 */

public class WeatherDataFetcher {
	private static final String TAG = "WeatherDataFetcher";

	/**
	 * Utility method to fetch the weather data from the URL specified. Takes as
	 * an input the location specified by the user. Returns a string of weather
	 * data
	 * 
	 * @param location
	 * @return weatherData
	 */
	public String fetchWeatherDataFromURL(String location) {
		HttpURLConnection weatherUrlConnection = null;
		InputStream weatherDataIs = null;
		try {
			weatherUrlConnection = (HttpURLConnection) (new URL(URLConstants.BASE_URL + location)).openConnection();
			weatherUrlConnection.setDoInput(true);
			weatherUrlConnection.setRequestMethod("GET");
			weatherUrlConnection.setDoOutput(true);
			weatherUrlConnection.connect();

			/*
			 * Response is got here after talking to the URL specified
			 */
			StringBuilder responseBuffer = new StringBuilder();
			weatherDataIs = weatherUrlConnection.getInputStream();
			BufferedReader urlBufferReader = new BufferedReader(new InputStreamReader(weatherDataIs));
			String line = null;
			while ((line = urlBufferReader.readLine()) != null)
				responseBuffer.append(line + "\r\n");
			weatherDataIs.close();
			weatherUrlConnection.disconnect();
			return responseBuffer.toString();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Log.e(TAG, ioe.getMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			try {
				weatherDataIs.close();
				weatherUrlConnection.disconnect();
			} catch (IOException ioe) {
				Log.e(TAG, ioe.getMessage());
			}
		}
		return null;
	}

	public byte[] getImage(String code) {
		HttpURLConnection con = null;
		InputStream is = null;
		try {
			con = (HttpURLConnection) (new URL(URLConstants.IMG_URL + code)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();

			// Let's read the response
			is = con.getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			while (is.read(buffer) != -1)
				baos.write(buffer);

			return baos.toByteArray();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}

		return null;

	}
}
