package com.weatherapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weatherapp.model.LocationModel;
import com.weatherapp.model.WeatherModel;

/**
 * This is a utility class to parse the weather data from Json. This can be
 * expanded to add more utilities
 * 
 * 
 */
public class WeatherDataParser {

	public static WeatherModel getWeatherInfoFromJson(String data) throws JSONException {
		WeatherModel weather = new WeatherModel();

		// Create the JsonObject
		JSONObject mainJsonObject = new JSONObject(data);

		// Create the Location Model object
		LocationModel locationModel = new LocationModel();

		JSONObject coordJsonObject = getJsonObject("coord", mainJsonObject);
		locationModel.setLatitude(getJsonFloat("lat", coordJsonObject));
		locationModel.setLongitude(getJsonFloat("lon", coordJsonObject));

		JSONObject sysObj = getJsonObject("sys", mainJsonObject);
		locationModel.setCountry(getJsonString("country", sysObj));
		locationModel.setSunrise(getJsonInt("sunrise", sysObj));
		locationModel.setSunset(getJsonInt("sunset", sysObj));
		locationModel.setCity(getJsonString("name", mainJsonObject));
		weather.location = locationModel;

		// Retrieve the Weather details
		JSONArray jArr = mainJsonObject.getJSONArray("weather");

		// We use only the first value
		JSONObject someMoreWeatherData = jArr.getJSONObject(0);
		weather.currentCondition.setWeatherId(getJsonInt("id", someMoreWeatherData));
		weather.currentCondition.setDescr(getJsonString("description", someMoreWeatherData));
		weather.currentCondition.setCondition(getJsonString("main", someMoreWeatherData));
		weather.currentCondition.setIcon(getJsonString("icon", someMoreWeatherData));

		JSONObject mainWeather = getJsonObject("main", mainJsonObject);
		weather.currentCondition.setHumidity(getJsonInt("humidity", mainWeather));
		weather.currentCondition.setPressure(getJsonInt("pressure", mainWeather));
		weather.temperature.setMaxTemp(getJsonFloat("temp_max", mainWeather));
		weather.temperature.setMinTemp(getJsonFloat("temp_min", mainWeather));
		weather.temperature.setTemp(getJsonFloat("temp", mainWeather));

		JSONObject windData = getJsonObject("wind", mainJsonObject);
		weather.wind.setSpeed(getJsonFloat("speed", windData));
		weather.wind.setDeg(getJsonFloat("deg", windData));

		JSONObject cloudData = getJsonObject("clouds", mainJsonObject);
		weather.clouds.setPerc(getJsonInt("all", cloudData));
		return weather;
	}

	private static JSONObject getJsonObject(String tag, JSONObject jsonObj) throws JSONException {
		JSONObject subObj = jsonObj.getJSONObject(tag);
		return subObj;
	}

	private static String getJsonString(String tag, JSONObject jsonObj) throws JSONException {
		return jsonObj.getString(tag);
	}

	private static float getJsonFloat(String tag, JSONObject jsonObj) throws JSONException {
		return (float) jsonObj.getDouble(tag);
	}

	private static int getJsonInt(String tag, JSONObject jsonObj) throws JSONException {
		return jsonObj.getInt(tag);
	}

}
