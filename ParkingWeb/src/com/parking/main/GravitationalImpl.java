package com.parking.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPBinding;

import com.parking.algorithms.GravitationalComputation;
import com.parking.constants.AppConstants;
import com.parking.constants.AppConstants.ALGORITHM_TYPE;
import com.parking.model.Location;
import com.parking.utils.DistanceUtils;
import com.parking.utils.GeneralUtils;

public class GravitationalImpl extends HttpServlet {

	private Timestamp driverTimeStamp;

	private Hashtable<Integer, Double> mGForceDistance;

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

	public int computeGravityRoadNetwork(final Location userLoc) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return -1;
		}
		try {
			AppConstants.dynamicProgress = 10;
			Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "cs440");

			Statement stmt = conn.createStatement();
			String query = "select * from parking.\"edges\"";
			ResultSet rs = stmt.executeQuery(query);
			mGForceDistance = new Hashtable<Integer, Double>();
			GravitationalComputation gComp = new GravitationalComputation();
			double temp = Integer.MAX_VALUE;
			while (rs.next()) {
				// TODO need to optimize the query

				int node1 = Integer.parseInt(rs.getString(7));
				int node2 = Integer.parseInt(rs.getString(8));

				Location node1Loc = GeneralUtils.getNodeLocation(node1);
				Location node2Loc = GeneralUtils.getNodeLocation(node2);

				// TODO Need to calculate based on latitude_2 and longitude_2
				double userToNode1Distance = 0;
				double userToNode2Distance = 0;
				double node1ToBlockDistance = 0;
				double node2ToBlockDistance = 0;

				double totalDistanceViaNode1 = 0;
				double totalDistanceViaNode2 = 0;

				System.out.println("-------START EXAMINATION-------------");
				System.out.println("Examination for block: " + rs.getString(1));

				userToNode1Distance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), node1Loc.getLatitude(),
						node1Loc.getLongitude(), 'M');

				System.out.println("userToNode1Distance: " + userToNode1Distance);

				userToNode2Distance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), node2Loc.getLatitude(),
						node2Loc.getLongitude(), 'M');

				System.out.println("userToNode2Distance: " + userToNode2Distance);

				node1ToBlockDistance = DistanceUtils.distance(node1Loc.getLatitude(), node1Loc.getLongitude(), Double.parseDouble(rs.getString(3)),
						Double.parseDouble(rs.getString(4)), 'M');

				System.out.println("node1ToBlockDistance: " + node1ToBlockDistance);

				node2ToBlockDistance = DistanceUtils.distance(node2Loc.getLatitude(), node2Loc.getLongitude(), Double.parseDouble(rs.getString(3)),
						Double.parseDouble(rs.getString(4)), 'M');

				System.out.println("node2ToBlockDistance: " + node2ToBlockDistance);

				totalDistanceViaNode1 = userToNode1Distance + node1ToBlockDistance;
				totalDistanceViaNode2 = userToNode2Distance + node2ToBlockDistance;

				System.out.println("totalDistanceViaNode1: " + totalDistanceViaNode1);
				System.out.println("totalDistanceViaNode2: " + totalDistanceViaNode2);

				double closestDistance = Math.min(totalDistanceViaNode1, totalDistanceViaNode2);

				if (temp > closestDistance) {
					temp = closestDistance;
				}

				int blockId = Integer.parseInt(rs.getString(1));
				int totalAvailableParkingLots = GeneralUtils.getAvailableParkingLots(blockId, driverTimeStamp);
				System.out.println("TotalAvailableParkingLots: " + totalAvailableParkingLots);

				double gForce = gComp.getGForce(totalAvailableParkingLots, closestDistance);
				if (Integer.parseInt(rs.getString(10)) > 0) {
					mGForceDistance.put(Integer.parseInt(rs.getString(1)), gForce);
				}

				System.out.println("GForce: " + gForce);
			}
			AppConstants.dynamicProgress = 30;

			int parkingBlock = GeneralUtils.getBlockWithMaxForce(mGForceDistance);
			HashMap<String, Location> blockLoc = GeneralUtils.getBlockLocation(parkingBlock);
			Location blockStartLoc = blockLoc.get("start");
			Location blockEndLoc = blockLoc.get("end");
			System.out.println("Block searched for driver at + " + driverTimeStamp + " instant of time: " + parkingBlock + " with a distance: "
					+ temp + " miles. This block is located between: " + blockStartLoc.getLatitude() + ", " + blockStartLoc.getLongitude() + " and "
					+ blockEndLoc.getLatitude() + ", " + blockEndLoc.getLongitude());

			System.out.println("&&&&&&&&&&&&&&&& END OF EXAMINATION &&&&&&&&&&&&&&&&&&\n\n");
			AppConstants.dynamicProgress = 50;
			// return parkingBlock;

			// Scale: Driver travels 1 meter in 1 minute

			int totalMinutes = 1;
			while (temp > 0) {
				AppConstants.dynamicProgress += 2;
				try {
					Thread.sleep(1000);
				} catch (Exception ex) {
					ex.printStackTrace();
					return -1;
				}
				temp = temp - 0.0310686; //
				// Subracting 50 meters from mile

				// Increase 1 second from the timestamp
				driverTimeStamp.setMinutes(driverTimeStamp.getMinutes() + 1);
				totalMinutes += 1;

				// System.out.println("driverTimeStamp is: " + driverTimeStamp);

				// TODO Check for max time-stamp that is less than
				// driverTimeStamp

				if (isParkingSpaceAvailable(parkingBlock)) {
					System.out.println("Block searched for driver at " + driverTimeStamp + " is " + parkingBlock + " with a distance: " + temp
							+ " miles");
					continue;
				} else {
					System.out.println("Parking Lot not available at : " + driverTimeStamp);
					// TODO Allocate a different block
				}
			}
			return totalMinutes;
		} catch (SQLException se) {
			se.printStackTrace();
			return -1;
		}
	}

	private void computeGravityFreeSpace(final Location userLoc) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "cs440");

			Statement stmt = conn.createStatement();
			String query = "select * from parking.\"edges\"";
			ResultSet rs = stmt.executeQuery(query);
			mGForceDistance = new Hashtable<Integer, Double>();
			GravitationalComputation gComp = new GravitationalComputation();
			double temp = Integer.MAX_VALUE;
			while (rs.next()) {
				// TODO need to optimize the query

				double closestDistance = Math.min(
						DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), Double.parseDouble(rs.getString(4)),
								Double.parseDouble(rs.getString(3)), 'M'),
						DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), Double.parseDouble(rs.getString(6)),
								Double.parseDouble(rs.getString(5)), 'M'));

				if (temp > closestDistance) {
					temp = closestDistance;
				}

				int blockId = Integer.parseInt(rs.getString(1));
				int totalAvailableParkingLots = GeneralUtils.getAvailableParkingLots(blockId, driverTimeStamp);

				double gForce = gComp.getGForce(totalAvailableParkingLots, closestDistance);
				if (Integer.parseInt(rs.getString(10)) > 0) {
					mGForceDistance.put(Integer.parseInt(rs.getString(1)), gForce);
				}
			}

			int parkingBlock = GeneralUtils.getBlockWithMaxForce(mGForceDistance);
			System.out.println("Block searched for driver at 2012-04-06:00:19:00 instant of time: " + parkingBlock + " with a distance: " + temp
					+ " miles");

			// Scale: Driver travels 1 meter in 1 sec

			while (temp > 0) {
				try {
					Thread.sleep(1000);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				temp = temp - 0.000621371; // Subracting 1 meter from mile

				// Increase 1 second from the timestamp
				driverTimeStamp.setSeconds(driverTimeStamp.getSeconds() + 1);

				// System.out.println("driverTimeStamp is: " + driverTimeStamp);

				// TODO Check for max time-stamp that is less than
				// driverTimeStamp

				if (isParkingSpaceAvailable(parkingBlock)) {
					continue;
				} else {
					// TODO Allocate a different block
				}

			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public void initializeDriverTime() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("23/04/2012");
			date.setHours(18);
			date.setMinutes(00); // TODO Set the Seconds to 10
			date.setSeconds(00);
			long time = date.getTime();
			driverTimeStamp = new Timestamp(time);

			// System.out.println(driverTimeStamp);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AppConstants.ALGORITHM_TYPE algorithmType = (AppConstants.ALGORITHM_TYPE) req.getAttribute(AppConstants.ALGORITHM_TYPE_KEY);
		switch (algorithmType) {
		case GRAVITATIONAL_DETERMINISTIC:
			GravitationalImpl rSearch = new GravitationalImpl();

			// Test: 37.806205, -122.424262

			// Note: Gravity is not good for UserLoc: 37.806464, -122.418864. It
			// should give me 546271, but gives 546281
			Location currentUserLoc = new Location(37.80348, -122.4115);

			// Initialize the driver timestamp to 2012-04-06:00:00:10:00
			rSearch.initializeDriverTime();

			System.out.println("Computing your parking lot. Please wait ...");
			/*
			 * ---> Uncomment for Gravitational Algorithm using Road Network
			 */
			rSearch.computeGravityRoadNetwork(currentUserLoc);
			break;
		case GREEDY_DETERMINISTIC:
			break;
		}
	}

	public static void main(String[] args) {
		GravitationalImpl rSearch = new GravitationalImpl();

		// Test: 37.806205, -122.424262

		// Note: Gravity is not good for UserLoc: 37.806464, -122.418864. It
		// should give me 546271, but gives 546281
		Location currentUserLoc = new Location(37.80348, -122.4115);

		// Initialize the driver timestamp to 2012-04-06:00:00:10:00
		rSearch.initializeDriverTime();

		System.out.println("Computing your parking lot. Please wait ...");

		// rSearch.computeGravityFreeSpace(currentUserLoc); --> Uncomment for
		// Gravitation Algorithm using Free Space
		rSearch.computeGravityRoadNetwork(currentUserLoc); // ---> Uncomment for
															// Gravitational
															// Algorithm using
															// Road Network

	}
}
