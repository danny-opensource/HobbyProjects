package com.parking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.parking.comparators.TrialDataComparator;
import com.parking.constants.AppConstants;
import com.parking.constants.AppConstants.ALGORITHM_TYPE;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.model.RoadNetworkNode;
import com.parking.model.TrialData;

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

	public static int getNearestParkingBlock(TreeMap<Integer, Double> minMap) {
		if (minMap != null && !minMap.isEmpty()) {
			NavigableMap<Integer, Double> parkingMap = minMap;
			if (parkingMap.lastKey() < 0) {
				System.out.println("*** DANGER! last Key: " + parkingMap.lastKey());
				System.out.println("*** DANGER! first Key: " + parkingMap.firstKey());
				return parkingMap.firstKey();
			}
			return parkingMap.lastKey();
		}
		return 100;

		/*
		 * Iterator<Integer> i = minMap.keySet().iterator(); return i.hasNext()
		 * ? i.next() : null;
		 */
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

	private static ArrayList<TrialData> getGraDetSimulatedData(final int congestionLevel) {
		ArrayList<TrialData> returnList = new ArrayList<TrialData>();
		switch (congestionLevel) {
		case 0:
			for (String key : AppConstants.sSimulatedGraDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("0")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraDetData.get(key));
				}
			}
			break;
		case 30:
			for (String key : AppConstants.sSimulatedGraDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("30")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraDetData.get(key));
				}
			}
			break;
		case 50:
			for (String key : AppConstants.sSimulatedGraDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("50")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraDetData.get(key));
				}
			}
			break;
		case 70:
			for (String key : AppConstants.sSimulatedGraDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("70")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraDetData.get(key));
				}
			}
			break;
		case 90:
			for (String key : AppConstants.sSimulatedGraDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("90")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraDetData.get(key));
				}
			}
			break;
		}
		Collections.sort(returnList, new TrialDataComparator());
		return returnList;
	}

	private static ArrayList<TrialData> getGreedyDetSimulatedData(final int congestionLevel) {

		ArrayList<TrialData> returnList = new ArrayList<TrialData>();
		switch (congestionLevel) {
		case 0:
			for (String key : AppConstants.sSimulatedGreedyDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("0")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyDetData.get(key));
				}
			}
			break;
		case 30:
			for (String key : AppConstants.sSimulatedGreedyDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("30")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyDetData.get(key));
				}
			}
			break;
		case 50:
			for (String key : AppConstants.sSimulatedGreedyDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("50")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyDetData.get(key));
				}
			}
			break;
		case 70:
			for (String key : AppConstants.sSimulatedGreedyDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("70")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyDetData.get(key));
				}
			}
			break;
		case 90:
			for (String key : AppConstants.sSimulatedGreedyDetData.keySet()) {
				System.out.println(key);
				if (key.startsWith("90")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyDetData.get(key));
				}
			}
			break;
		}
		Collections.sort(returnList, new TrialDataComparator());
		return returnList;

	}

	private static ArrayList<TrialData> getGraProbSimulatedData(final int congestionLevel) {

		ArrayList<TrialData> returnList = new ArrayList<TrialData>();
		switch (congestionLevel) {
		case 0:
			for (String key : AppConstants.sSimulatedGraProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("0")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraProbData.get(key));
				}
			}
			break;
		case 30:
			for (String key : AppConstants.sSimulatedGraProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("30")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraProbData.get(key));
				}
			}
			break;
		case 50:
			for (String key : AppConstants.sSimulatedGraProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("50")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraProbData.get(key));
				}
			}
			break;
		case 70:
			for (String key : AppConstants.sSimulatedGraProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("70")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraProbData.get(key));
				}
			}
			break;
		case 90:
			for (String key : AppConstants.sSimulatedGraProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("90")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGraProbData.get(key));
				}
			}
			break;
		}
		Collections.sort(returnList, new TrialDataComparator());
		return returnList;
	}

	private static ArrayList<TrialData> getGreedyProbSimulatedData(final int congestionLevel) {

		ArrayList<TrialData> returnList = new ArrayList<TrialData>();
		switch (congestionLevel) {
		case 0:
			for (String key : AppConstants.sSimulatedGreedyProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("0")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyProbData.get(key));
				}
			}
			break;
		case 30:
			for (String key : AppConstants.sSimulatedGreedyProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("30")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyProbData.get(key));
				}
			}
			break;
		case 50:
			for (String key : AppConstants.sSimulatedGreedyProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("50")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyProbData.get(key));
				}
			}
			break;
		case 70:
			for (String key : AppConstants.sSimulatedGreedyProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("70")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyProbData.get(key));
				}
			}
			break;
		case 90:
			for (String key : AppConstants.sSimulatedGreedyProbData.keySet()) {
				System.out.println(key);
				if (key.startsWith("90")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sSimulatedGreedyProbData.get(key));
				}
			}
			break;
		}
		Collections.sort(returnList, new TrialDataComparator());
		return returnList;
	}

	public static ArrayList<TrialData> getTrialData(final int congestionLevel) {
		ArrayList<TrialData> returnList = new ArrayList<TrialData>();
		switch (congestionLevel) {
		case 0:
			for (String key : AppConstants.sGravitationalTraialData.keySet()) {
				System.out.println(key);
				if (key.startsWith("0")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sGravitationalTraialData.get(key));
				}
			}
			break;
		case 30:
			for (String key : AppConstants.sGravitationalTraialData.keySet()) {
				System.out.println(key);
				if (key.startsWith("30")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sGravitationalTraialData.get(key));
				}
			}
			break;
		case 50:
			for (String key : AppConstants.sGravitationalTraialData.keySet()) {
				System.out.println(key);
				if (key.startsWith("50")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sGravitationalTraialData.get(key));
				}
			}
			break;
		case 70:
			for (String key : AppConstants.sGravitationalTraialData.keySet()) {
				System.out.println(key);
				if (key.startsWith("70")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sGravitationalTraialData.get(key));
				}
			}
			break;
		case 90:
			for (String key : AppConstants.sGravitationalTraialData.keySet()) {
				System.out.println(key);
				if (key.startsWith("90")) {
					System.out.println("Adding: " + key + " to the : " + congestionLevel + " congestionLevel list");
					returnList.add(AppConstants.sGravitationalTraialData.get(key));
				}
			}
			break;
		}
		Collections.sort(returnList, new TrialDataComparator());
		return returnList;
	}

	public static ArrayList<TrialData> getSimulatedTrialData(final int congestionLevel, final AppConstants.ALGORITHM_TYPE algorithmType) {
		ArrayList<TrialData> returnList = null;
		switch (algorithmType) {
		case GRAVITATIONAL_DETERMINISTIC:
			returnList = getGraDetSimulatedData(congestionLevel);
			break;
		case GREEDY_DETERMINISTIC:
			returnList = getGreedyDetSimulatedData(congestionLevel);
			break;
		case GRAVITATIONAL_PROBABILISTIC:
			returnList = getGraProbSimulatedData(congestionLevel);
			break;
		case GREEDY_PROBABILISTIC:
			returnList = getGreedyProbSimulatedData(congestionLevel);
			break;
		}
		return returnList;
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

		System.out.println("^^^^^^^^^^driverTimeStamp in utils: " + driverTimeStamp);

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
			query = "select max(p.timestamp) from parking.\"projection\" p where p.block_id=" + block + "and p.timestamp <'" + driverTimeStamp + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				timeStamp = rs.getString(1);
			}
			rs.close();
			stmt.close();

			if (timeStamp == null)
				return 0;
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
			/*
			 * if (block == 612311) {
			 * System.out.println("612311 parking avail: " + parkingLotCount +
			 * " for congession: " + congestionLevel); }
			 */
			return parkingLotCount;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null)
					stmt.close();

				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static double getEstimatedParkingLots(final int blockId, final Timestamp driverTimeStamp, final int congestionLevel) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int hour = driverTimeStamp.getHours();
		int dayOfWeek = driverTimeStamp.getDay();

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
			/*
			 * query =
			 * "select max(p.timestamp) from parking.\"prob_data\" p where p.block_id="
			 * + blockId + "and p.timestamp <'" + driverTimeStamp +
			 * "' group by p.available";
			 */

			switch (congestionLevel) {
			case 0:
				query = "select avg from parking.\"prob_data\" p where p.block_id=" + blockId + " and time=" + hour + " and dow=" + dayOfWeek;
				break;
			case 30:
				query = "select avg from parking.\"prob_data_thirty\" p where p.block_id=" + blockId + " and time=" + hour + " and dow=" + dayOfWeek;
				break;
			case 50:
				query = "select avg from parking.\"prob_data_fifty\" p where p.block_id=" + blockId + " and time=" + hour + " and dow=" + dayOfWeek;
				break;
			case 70:
				query = "select avg from parking.\"prob_data_seventy\" p where p.block_id=" + blockId + " and time=" + hour + " and dow=" + dayOfWeek;
				break;
			case 90:
				query = "select avg from parking.\"prob_data_ninety\" p where p.block_id=" + blockId + " and time=" + hour + " and dow=" + dayOfWeek;
				break;
			default:
				query = "select avg from parking.\"prob_data\" p where p.block_id=" + blockId + " and time=" + hour + " and dow=" + dayOfWeek;
				break;
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			double estimatedFreeBlocks = 0;
			if (rs.next()) {
				estimatedFreeBlocks = Double.parseDouble(rs.getString(1));
			}

			return estimatedFreeBlocks;
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
