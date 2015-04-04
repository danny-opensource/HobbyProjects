package com.parking.maps;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OSMMaps {

	private final String OSM_BASE_URL = "http://router.project-osrm.org/viaroute?";

	public String getDistance(double startLat, double startLong, double endLat, double endLong) {
		try {
			StringBuilder locationBuilder = new StringBuilder(OSM_BASE_URL);
			locationBuilder.append("loc=").append(startLat).append(",").append(startLong).append("&loc=").append(endLat).append(",").append(endLong);
			URL url = new URL(locationBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.connect();

			int status = conn.getResponseCode();

			if (status == HttpURLConnection.HTTP_OK) {
				InputStream reader = conn.getInputStream();
				URLConnection.guessContentTypeFromStream(reader);
				StringBuilder builder = new StringBuilder();
				int a = 0;
				while ((a = reader.read()) != -1) {
					builder.append((char) a);
				}
				// System.out.println(builder.toString());
				String distance = fetchDistance(builder.toString());
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

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String getTotalTimeToParkingBlock(double startLat, double startLong, double endLat, double endLong) {
		try {
			StringBuilder locationBuilder = new StringBuilder(OSM_BASE_URL);
			locationBuilder.append("loc=").append(startLat).append(",").append(startLong).append("&loc=").append(endLat).append(",").append(endLong);
			URL url = new URL(locationBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.connect();

			int status = conn.getResponseCode();

			if (status == HttpURLConnection.HTTP_OK) {
				InputStream reader = conn.getInputStream();
				URLConnection.guessContentTypeFromStream(reader);
				StringBuilder builder = new StringBuilder();
				int a = 0;
				while ((a = reader.read()) != -1) {
					builder.append((char) a);
				}
				// System.out.println(builder.toString());
				String distance = fetchTotalTime(builder.toString());
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

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private String fetchTotalTime(final String builderString) {

		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(builderString);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.exit(1);
		}
		JSONObject routeSummary = (JSONObject) response.get("route_summary"); //
		String totalDistance = routeSummary.get("total_time").toString();

		return totalDistance;
	}

	private String fetchDistance(final String builderString) {

		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(builderString);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.exit(1);
		}
		JSONObject routeSummary = (JSONObject) response.get("route_summary"); //
		String totalDistance = routeSummary.get("total_distance").toString();

		double distanceMeters = Double.parseDouble(totalDistance);
		distanceMeters *= 0.000621371;

		String strDistanceMiles = distanceMeters + "";

		return strDistanceMiles;
	}

	public static void main(String[] args) {
		OSMMaps maps = new OSMMaps();
		maps.getDistance(37.806189, -122.424281, 37.795406, -122.412436);
	}
}
