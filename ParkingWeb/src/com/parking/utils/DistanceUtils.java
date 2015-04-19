package com.parking.utils;

import com.parking.constants.AppConstants;
import com.parking.maps.GoogleDistance;
import com.parking.maps.OSMMaps;
import com.parking.model.Location;

/**
 * General Distance Utility class that acts as a facade for handling distance
 * and time related functionalities
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class DistanceUtils {

	private static GoogleDistance sGoogleInstance;
	private static OSMMaps sOSMMaps;

	private DistanceUtils() {
		System.out.println("Sorry. This class cannot be instantiated. It is a Singleton!");
	}

	public static GoogleDistance getGDistanceInstance() {
		if (sGoogleInstance == null) {
			sGoogleInstance = new GoogleDistance();
			return sGoogleInstance;
		}
		return sGoogleInstance;
	}

	public static OSMMaps getOSMMapsInstance() {
		if (sOSMMaps == null) {
			sOSMMaps = new OSMMaps();
			return sOSMMaps;
		}
		return sOSMMaps;
	}

	public static Location latlong(final Location startPoint, final double bearing, final double distance) {

		return null;
	}

	public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {

		double distance = 0;
		if (!AppConstants.sInMemoryDistance.containsKey((lat1 + "$" + lon1 + "$" + lat2 + "$" + lon2))) {
			System.out.println("***** cache MISS for Distance!!!!");
			OSMMaps osmMapsInstance = DistanceUtils.getOSMMapsInstance();
			distance = Double.parseDouble(osmMapsInstance.getDistance(lat1, lon1, lat2, lon2));
			AppConstants.sInMemoryDistance.put((lat1 + "$" + lon1 + "$" + lat2 + "$" + lon2), distance);
		} else {
			System.out.println("***** cache HIT for Distance!!!!");
			distance = AppConstants.sInMemoryDistance.get((lat1 + "$" + lon1 + "$" + lat2 + "$" + lon2));
		}
		return distance;
	}

	public static int totalTime(double lat1, double lon1, double lat2, double lon2, char unit) {

		int totalTimeInSecs = 0;
		if (!AppConstants.sInMemoryTotalTime.containsKey((lat1 + "$" + lon1 + "$" + lat2 + "$" + lon2))) {
			System.out.println("***** cache MISS for Time!!!!");
			OSMMaps osmMapsInstance = DistanceUtils.getOSMMapsInstance();
			totalTimeInSecs = Integer.parseInt(osmMapsInstance.getTotalTimeToParkingBlock(lat1, lon1, lat2, lon2));
			AppConstants.sInMemoryTotalTime.put((lat1 + "$" + lon1 + "$" + lat2 + "$" + lon2), totalTimeInSecs);
		} else {
			System.out.println("***** cache HIT for Time!!!!");
			totalTimeInSecs = AppConstants.sInMemoryTotalTime.get((lat1 + "$" + lon1 + "$" + lat2 + "$" + lon2));
		}

		// System.out.println("*** Double Distance: " + distance);
		return totalTimeInSecs;
	}

	public static double anotherDistance(double lat1, double lon1, double lat2, double lon2, char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	/*
	 * This function converts decimal degrees to radians
	 */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*
	 * This method converts radians to decimal degrees
	 */
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}
