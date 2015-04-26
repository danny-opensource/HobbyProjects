package com.parking.model;

import java.io.Serializable;

public class TrialData implements Serializable {

	private static final long serialVersionUID = 1L;
	private int trialNumber;
	private int parkingBlockId;
	private int timeToParkingBlock;
	private Location userLocation;
	private Location parkingBlockLocation;
	private int congestionLevel;

	public TrialData(final int parkingBlockId, final int timeToParkingBlock, final Location userLocation, final Location parkingBlockLocation) {
		this.parkingBlockId = parkingBlockId;
		this.timeToParkingBlock = timeToParkingBlock;
		this.userLocation = userLocation;
		this.parkingBlockLocation = parkingBlockLocation;
	}

	public void setTrialNumber(final int trialNumber) {
		this.trialNumber = trialNumber;
	}

	public void setCongestionLevel(final int congestionLevel) {
		this.congestionLevel = congestionLevel;
	}

	public int getTrianNumber() {
		return trialNumber;
	}

	public int getParkingBlockId() {
		return parkingBlockId;
	}

	public int getTimeToParkingBlock() {
		return timeToParkingBlock;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public Location getParkingBlockLocation() {
		return parkingBlockLocation;
	}
}
