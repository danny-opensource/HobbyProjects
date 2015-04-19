package com.parking.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.parking.constants.AppConstants;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.model.TrialData;
import com.parking.utils.DistanceUtils;
import com.parking.utils.GeneralUtils;

/**
 * Core Greedy Algorithm Implementer
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class GreedyImpl {

	private Timestamp driverTimeStamp;

	private TreeMap<Integer, Double> mBlockDistanceSortedMap;

	private boolean isParkingSpaceAvailable(final int block) {
		/*
		 * TODO Check if parking space is available for the block at the
		 * driverTimeStamp
		 */

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return false;
		}
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "cs440");
			String query = "select p.block_id, max(p.timestamp) from parking.\"projection\" p where p.block_id=" + block + "and p.timestamp <'"
					+ driverTimeStamp + "' group by p.block_id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				// System.out.println("User's timestamp: " + driverTimeStamp);
				// System.out.println("Chosen Timestamp: " + rs.getString(2));

				int parkingLotCount = Integer.parseInt(rs.getString(1));
				if (parkingLotCount > 0) {
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public TrialData computeGravityRoadNetwork(final Location userLoc, final int congestionLevel) {
		Iterator<Integer> it = AppConstants.sInMemoryEdges.getKeySet().iterator();
		mBlockDistanceSortedMap = new TreeMap<Integer, Double>();
		while (it.hasNext()) {
			RoadNetworkEdge edge = AppConstants.sInMemoryEdges.getEdge(it.next());
			double userToBlockDistance = 0;
			userToBlockDistance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), edge.latitude1, edge.longitude1, 'M');
			int blockId = edge.blockId;
			int totalAvailableParkingLots = GeneralUtils.getAvailableParkingLots(blockId, driverTimeStamp, congestionLevel);
			System.out.println("**** totalAvailableParkingLots: " + totalAvailableParkingLots);
			System.out.println("*** Num Operational: " + edge.numOperational);
			double resultantValue;
			if (edge.numOperational == 0) {
				resultantValue = 0;
			} else {
				resultantValue = (totalAvailableParkingLots / (edge.numOperational - (edge.numOperational * (congestionLevel / 100))))
						/ userToBlockDistance;
			}
			if (edge.numOperational > 0 && totalAvailableParkingLots > 0) {
				mBlockDistanceSortedMap.put(edge.blockId, resultantValue);
			}
		}
		TreeMap<Integer, Double> sortedDistanceMap = (TreeMap<Integer, Double>) GeneralUtils.sortByValues(mBlockDistanceSortedMap);
		Iterator testIt = sortedDistanceMap.keySet().iterator();
		/*
		 * while (testIt.hasNext()) { System.out.println("Test: " +
		 * testIt.next() + " with value: " +
		 * sortedDistanceMap.get(testIt.next())); }
		 */

		for (Map.Entry<Integer, Double> entry : sortedDistanceMap.entrySet()) {
			System.out.print(entry.getKey() + "/" + entry.getValue() + "....");
			Location loc = GeneralUtils.getBlockLocation(entry.getKey()).get("start");
			System.out.println("Distance is: "
					+ DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), loc.getLatitude(), loc.getLongitude(), 'M'));
		}

		int parkingBlock = GeneralUtils.getNearestParkingBlock(sortedDistanceMap);
		System.out.println("*** Parking Block got : " + parkingBlock);
		HashMap<String, Location> blockLoc = GeneralUtils.getBlockLocation(parkingBlock);
		Location blockStartLoc = blockLoc.get("start");

		double userToBestParkingDistance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), blockStartLoc.getLatitude(),
				blockStartLoc.getLongitude(), 'M');

		System.out.println("The Parking Block: " + parkingBlock + " allocated is located at a distance of: " + userToBestParkingDistance
				+ " from the user location");

		int totalTime = DistanceUtils.totalTime(userLoc.getLatitude(), userLoc.getLongitude(), blockStartLoc.getLatitude(),
				blockStartLoc.getLongitude(), 'M');

		// NAVIGATION PART WHILE - BAD CODE. RE-INVENT!
		while (userToBestParkingDistance > 0) {
			userToBestParkingDistance = userToBestParkingDistance - 0.0310686; //
			// Subracting 50 meters from mile

			// Increase 1 minute from the timestamp
			driverTimeStamp.setMinutes(driverTimeStamp.getMinutes() + 1);

			// TODO Check for max time-stamp that is less than
			// driverTimeStamp

			if (isParkingSpaceAvailable(parkingBlock)) {
				continue;
			} else {
				System.out.println("Parking Lot not available at : " + driverTimeStamp);
				// TODO Allocate a different block
			}
		}

		System.out.println("Total Minutes to the parking lot in seconds: " + totalTime);
		TrialData returnTrialData = new TrialData(parkingBlock, totalTime, userLoc, blockStartLoc);
		return returnTrialData;
	}

	public void initializeDriverTime() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("23/04/2012");
			date.setHours(9);
			date.setMinutes(00); // TODO Set the Seconds to 10
			date.setSeconds(00);
			long time = date.getTime();
			driverTimeStamp = new Timestamp(time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
