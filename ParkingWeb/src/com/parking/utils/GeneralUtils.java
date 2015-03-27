package com.parking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.parking.constants.AppConstants;
import com.parking.constants.AppConstants.ALGORITHM_TYPE;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.model.RoadNetworkNode;

public class GeneralUtils {

	public static int getBlockWithMaxForce(Map<Integer, Double> minMap) {

		double maxValue = Integer.MIN_VALUE;
		int returnKey = 0;

		Iterator<Integer> it = minMap.keySet().iterator();

		while (it.hasNext()) {
			int key = it.next();
			double value = minMap.get(key);
			if (maxValue < value) {
				maxValue = value;
				returnKey = key;
			}
		}

		return returnKey;
	}

	public static HashMap<String, Location> getBlockLocation(final int blockId) {

		HashMap<String, Location> blockLocation = new HashMap<String, Location>();
		RoadNetworkEdge edge = AppConstants.sInMemoryEdges.getEdge(blockId);

		double startLatitude = 0;
		double startLongitude = 0;
		double endLatitude = 0;
		double endLongitude = 0;

		startLatitude = edge.latitude1;
		startLongitude = edge.longitude1;
		endLatitude = edge.latitude2;
		endLongitude = edge.longitude2;

		Location startLocation = new Location(startLatitude, startLongitude);
		blockLocation.put("start", startLocation);
		Location endLocation = new Location(endLatitude, endLongitude);
		blockLocation.put("end", endLocation);

		return blockLocation;
	}

	public static Location getNodeLocation(final int nodeId) {

		RoadNetworkNode node = AppConstants.sInMemoryNodes.getNode(nodeId);
		Location location = new Location(node.latitude, node.longitude);
		return location;
	}

	public static int getAvailableParkingLots(final int block, final Timestamp driverTimeStamp) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return -1;
		}
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "cs440");
			String query = "select p.available, max(p.timestamp) from parking.\"projection\" p where p.block_id=" + block + "and p.timestamp <'"
					+ driverTimeStamp + "' group by p.available";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			int parkingLotCount = 0;
			if (rs.next()) {
				System.out.println("*** Chosen timestamp: " + rs.getString(2));
				parkingLotCount = Integer.parseInt(rs.getString(1));
			}
			return parkingLotCount;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static AppConstants.ALGORITHM_TYPE getAlgorithmType(int type) {
		switch (type) {
		case 0:
			return ALGORITHM_TYPE.GRAVITATIONAL_DETERMINISTIC;
		case 1:
			return ALGORITHM_TYPE.GREEDY_DETERMINISTIC;
		default:
			return ALGORITHM_TYPE.GRAVITATIONAL_DETERMINISTIC;
		}
	}
}
