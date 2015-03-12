package com.parking.constants;

import java.util.HashMap;

import com.parking.model.Location;

public class AppConstants {

	public static final String ALGORITHM_TYPE_KEY = "algorithmType";
	public static enum ALGORITHM_TYPE {
		GRAVITATIONAL_DETERMINISTIC, GREEDY_DETERMINISTIC;
	}
	public static volatile int dynamicProgress = 0;
	public static HashMap<Integer, Location> randomUserLocations;

}
