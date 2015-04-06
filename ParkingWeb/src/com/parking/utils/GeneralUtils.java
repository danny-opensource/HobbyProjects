package com.parking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.parking.constants.AppConstants;
import com.parking.constants.AppConstants.ALGORITHM_TYPE;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.model.RoadNetworkNode;

/**
 * All other utility functions that cannot be categorized under Database and
 * Distance Utils are here.
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class GeneralUtils {

	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {

			@Override
			public int compare(K k1, K k2) {
				int compare = map.get(k1).compareTo(map.get(k2));
				if (compare == 0)
					return 1;
				return compare;
			}
		};
		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	public static int getNearestParkingBlock(Map<Integer, Double> minMap) {
		Iterator<Integer> i = minMap.keySet().iterator();
		return i.hasNext() ? i.next() : null;
	}

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

	public static int getAvailableParkingLots(final int block, final Timestamp driverTimeStamp, final int congestionLevel) {
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
			String query = "";
			String secondQuery = "";
			String timeStamp = "";
			query = "select max(p.timestamp) from parking.\"projection\" p where p.block_id=" + block + "and p.timestamp <'" + driverTimeStamp
					+ "' group by p.available";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				timeStamp = rs.getString(1);
			}
			rs.close();
			stmt.close();
			switch (congestionLevel) {
			case 0:

				/*
				 * query =
				 * "select p.available, max(p.timestamp) from parking.\"projection\" p where p.block_id="
				 * + block + "and p.timestamp <'" + driverTimeStamp +
				 * "' group by p.available";
				 */
				secondQuery = "select p.available from parking.\"projection\" p where p.timestamp='" + timeStamp + "' and block_id=" + block;
				// System.out.println("Getting Data for Congestion Level 0%: " +
				// query);
				break;
			case 30:

				secondQuery = "select p.available from parking.\"projection_thirty\" p where p.timestamp='" + timeStamp + "' and block_id=" + block;
				// System.out.println("Getting Data for Congestion Level 30%: "
				// + query);
				break;
			case 50:
				// System.out.println("Getting Data for Congestion Level 50%");
				secondQuery = "select p.available from parking.\"projection_fifty\" p where p.timestamp='" + timeStamp + "' and block_id=" + block;
				break;
			case 70:
				// System.out.println("Getting Data for Congestion Level 70%");
				secondQuery = "select p.available from parking.\"projection_seventy\" p where p.timestamp='" + timeStamp + "' and block_id=" + block;
				break;
			case 90:
				// System.out.println("Getting Data for Congestion Level 90%");
				secondQuery = "select p.available from parking.\"projection_ninety\" p where p.timestamp='" + timeStamp + "' and block_id=" + block;
				break;
			default:
				secondQuery = "select p.available from parking.\"projection\" p where p.timestamp='" + timeStamp + "' and block_id=" + block;
				break;
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery(secondQuery);

			int parkingLotCount = 0;
			if (rs.next()) {
				parkingLotCount = Integer.parseInt(rs.getString(1));
			}
			/*if (block == 612311) {
				System.out.println("612311 parking avail: " + parkingLotCount + " for congession: " + congestionLevel);
			}*/
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
