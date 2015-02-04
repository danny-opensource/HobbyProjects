package com.parking.model;

public class UserLocation {

	private double mLatitude;
	private double mLongitude;

	public UserLocation(final double latitude, final double longitude) {
		mLatitude = latitude;
		mLongitude = longitude;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

}
