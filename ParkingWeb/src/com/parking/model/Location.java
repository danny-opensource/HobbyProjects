package com.parking.model;

public class Location {

	private double mLatitude;
	private double mLongitude;

	public Location(final double latitude, final double longitude) {
		mLatitude = latitude;
		mLongitude = longitude;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new String(mLatitude + "," + mLongitude);
	}
}