package com.parking.model;

public class LocationNavigation {

	private Location startLocation;
	private Location endLocation;

	public LocationNavigation(final Location startLocation, final Location endLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

}
