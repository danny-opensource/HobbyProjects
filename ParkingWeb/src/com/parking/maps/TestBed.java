package com.parking.maps;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.parking.constants.AppConstants;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.utils.DistanceUtils;
import com.parking.utils.GeneralUtils;

/**
 * Test bed for quick testing of APIs
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class TestBed {
	public static void main(String[] args) {
		double anotherDistVal = DistanceUtils.anotherDistance(37.806205, -122.424262, 37.803293, -122.417202, 'M');
		/*
		 * double googleDistance = DistanceUtils.distance(37.806205,
		 * -122.424262, 37.803293, -122.417202, 'M');
		 * 
		 * //System.out.println("Another Distance: " + anotherDistVal);
		 * //System.out.println("Google Distance: " + googleDistance);
		 * 
		 * double totalDistance = DistanceUtils.distance(37.806205, -122.424262,
		 * 37.803293, -122.417202, 'M'); double totalTime =
		 * DistanceUtils.totalTime(37.806205, -122.424262, 37.803293,
		 * -122.417202, 'M'); System.out.println("Total Distance: " +
		 * totalDistance); System.out.println("Total Time: " + totalTime);
		 */

		Location userLoc = new Location(37.806205, -122.424262);
		Iterator<Integer> it = AppConstants.sInMemoryEdges.getKeySet().iterator();
		TreeMap<Integer, Double> mBlockDistanceSortedMap = new TreeMap<Integer, Double>();
		while (it.hasNext()) {
			RoadNetworkEdge edge = AppConstants.sInMemoryEdges.getEdge(it.next());
			double userToBlockDistance = 0;
			userToBlockDistance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), edge.latitude1, edge.longitude1, 'M');
			int blockId = edge.blockId;
			// int totalAvailableParkingLots =
			// GeneralUtils.getAvailableParkingLots(blockId, driverTimeStamp,
			// congestionLevel);
			if (edge.numOperational > 0) {
				mBlockDistanceSortedMap.put(edge.blockId, userToBlockDistance);
			}
		}
		Map sortedDistanceMap = GeneralUtils.sortByValues(mBlockDistanceSortedMap);

		Set set = sortedDistanceMap.entrySet();

		Iterator i = set.iterator();

		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.println(me.getKey() + " : " + me.getValue());
		}
	}
}
