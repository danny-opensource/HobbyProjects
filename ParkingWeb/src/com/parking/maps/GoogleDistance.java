package com.parking.maps;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Utility to fetch the distance and navigation details using Google APIs.
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class GoogleDistance {
	private final String MAP_API = "https://maps.googleapis.com/maps/api";

	private String run(String type, String format, ParameterNameValue[] params) throws Exception {
		String query = "";
		StringBuilder locationBuilder = new StringBuilder(MAP_API + "/" + type + "/" + format + "?");
		for (int i = 0; i < params.length; i++) {
			if (i > 0)
				locationBuilder.append('&');
			locationBuilder.append(params[i].name).append('=').append(params[i].value);
			if (params[i].name.equals("query")) {
				query = params[i].value;
			}
		}
		String location = locationBuilder.toString();
		// System.out.println(location);
		URL url = new URL(location);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		HttpURLConnection.setFollowRedirects(true);
		conn.setDoInput(true);
		conn.connect();

		int status = conn.getResponseCode();
		while (true) {
			int wait = 0;
			String header = conn.getHeaderField("Retry-After");
			if (header != null)
				wait = Integer.valueOf(header);
			if (wait == 0)
				break;
			conn.disconnect();
			Thread.sleep(wait * 1000);
			conn = (HttpURLConnection) new URL(location).openConnection();
			conn.setDoInput(true);
			conn.connect();
			status = conn.getResponseCode();
		}
		if (status == HttpURLConnection.HTTP_OK) {
			InputStream reader = conn.getInputStream();
			URLConnection.guessContentTypeFromStream(reader);
			StringBuilder builder = new StringBuilder();
			int a = 0;
			while ((a = reader.read()) != -1) {
				builder.append((char) a);
			}
			// System.out.println(builder.toString());
			String distance = FetchDirection(builder.toString());
			StringBuilder distanceBuilder = new StringBuilder();

			String actualDistance = "";
			if (distance.contains("ft")) {
				distance = distance.replace("ft", "");
				distance = distance.trim();
				double ft = Double.parseDouble(distance);
				ft *= 0.000189394;
				distance = ft + "";
			} else {
				distance = distance.replace("mi", "");
			}
			actualDistance = distance.trim();
			return actualDistance;

		} else
			conn.disconnect();
		return "";
	}

	public String getDistance(final double startLat, double startLong, double destLat, double destLong) {
		String returnDistance = null;
		try {
			returnDistance = run("distancematrix", "json", new ParameterNameValue[] { new ParameterNameValue("origins", startLat + "," + startLong),
					new ParameterNameValue("destinations", destLat + "," + destLong), new ParameterNameValue("sensor", "false"),
					new ParameterNameValue("key", "AIzaSyAz1JAk83jhqzG85RObM3wTrlV6txGq3TM"), new ParameterNameValue("units", "imperial") });
		} catch (Exception ex) {
			System.out.println("Catching a Distance related exception");
			ex.printStackTrace();
			return null;
		}
		return returnDistance;
	}

	private static class ParameterNameValue {
		private final String name;
		private final String value;

		public ParameterNameValue(String name, String value) throws UnsupportedEncodingException {
			this.name = URLEncoder.encode(name, "UTF-8");
			this.value = URLEncoder.encode(value, "UTF-8");
		}
	}

	private String FetchDirection(String a) throws IOException, JSONException {
		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(a);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.exit(1);
		}
		// System.out.println(response.toString());
		JSONObject elements = (JSONObject) ((JSONArray) response.get("rows")).get(0);
		// System.out.println(elements.toString());
		JSONObject element = (JSONObject) ((JSONArray) elements.get("elements")).get(0);
		// System.out.println(element.toString());
		JSONObject distance = (JSONObject) element.get("distance");
		String dist = distance.get("text").toString();
		return dist;
	}
}
