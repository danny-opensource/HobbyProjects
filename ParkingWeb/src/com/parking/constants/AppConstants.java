package com.parking.constants;

import java.util.HashMap;

import com.parking.inmemory.BlockProjections;
import com.parking.inmemory.RoadEdges;
import com.parking.inmemory.RoadNodes;
import com.parking.model.Location;

public class AppConstants {

	public static final String ALGORITHM_TYPE_KEY = "algorithmType";

	public static enum ALGORITHM_TYPE {
		GRAVITATIONAL_DETERMINISTIC, GREEDY_DETERMINISTIC;
	}

	public static HashMap<Integer, Location> randomUserLocations;
	public static RoadNodes sInMemoryNodes = new RoadNodes();
	public static RoadEdges sInMemoryEdges = new RoadEdges();

}
