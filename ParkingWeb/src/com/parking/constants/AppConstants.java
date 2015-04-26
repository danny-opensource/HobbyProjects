package com.parking.constants;

import java.util.HashMap;

import com.parking.inmemory.RoadEdges;
import com.parking.inmemory.RoadNodes;
import com.parking.model.Location;
import com.parking.model.TrialData;

/**
 * Constants used through out the application
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class AppConstants {

	public static final String ALGORITHM_TYPE_KEY = "algorithmType";

	public static enum ALGORITHM_TYPE {
		GRAVITATIONAL_DETERMINISTIC, GREEDY_DETERMINISTIC, GRAVITATIONAL_PROBABILISTIC, GREEDY_PROBABILISTIC;
	}

	public static HashMap<Integer, Location> randomUserLocations;
	public static RoadNodes sInMemoryNodes = new RoadNodes();
	public static RoadEdges sInMemoryEdges = new RoadEdges();
	public static HashMap<String, TrialData> sGravitationalTraialData = new HashMap<String, TrialData>();
	public static HashMap<String, Double> sInMemoryDistance = new HashMap<String, Double>();
	public static HashMap<String, Integer> sInMemoryTotalTime = new HashMap<String, Integer>();
	public static HashMap<String, Double> sSimulatedDataForSevenAM = new HashMap<String, Double>();
	public static HashMap<String, TrialData> sSimulatedGraDetData = new HashMap<String, TrialData>();
	public static HashMap<String,TrialData> sSimulatedGreedyDetData = new HashMap<String, TrialData>();
	public static HashMap<String, TrialData> sSimulatedGraProbData = new HashMap<String, TrialData>();
	public static HashMap<String, TrialData> sSimulatedGreedyProbData = new HashMap<String, TrialData>();
	

}
