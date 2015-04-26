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

import com.parking.algorithms.GravitationalComputation;
import com.parking.constants.AppConstants;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.model.TrialData;
import com.parking.utils.DistanceUtils;
import com.parking.utils.GeneralUtils;

/**
 * Core Gravitational Algorithm Implementater.
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class ProbabilisticGravitationalImpl extends HttpServlet {

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

	public TrialData computeGravityRoadNetwork(final Location userLoc, final int congestionLevel) {
		Iterator<Integer> it = AppConstants.sInMemoryEdges.getKeySet().iterator();
		mGForceDistance = new Hashtable<Integer, Double>();
		GravitationalComputation gComp = new GravitationalComputation();
		while (it.hasNext()) {
			RoadNetworkEdge edge = AppConstants.sInMemoryEdges.getEdge(it.next());
			double userToBlockDistance = 0;
			userToBlockDistance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), edge.latitude1, edge.longitude1, 'M');
			int blockId = edge.blockId;

			double estimatedAvailableParkingLots = GeneralUtils.getEstimatedParkingLots(blockId, driverTimeStamp, congestionLevel);
			double gForce = gComp.getEstimatedGForce(estimatedAvailableParkingLots, userToBlockDistance);
			System.out.println("Block: " + blockId + " : GForce:" + gForce + " : congestionLevel: " + congestionLevel);
			if (edge.numOperational > 0) {
				mGForceDistance.put(edge.blockId, gForce);
			}
		}

		int parkingBlock = GeneralUtils.getBlockWithMaxForce(mGForceDistance);
		HashMap<String, Location> blockLoc = GeneralUtils.getBlockLocation(parkingBlock);
		Location blockStartLoc = blockLoc.get("start");
		Location blockEndLoc = blockLoc.get("end");

		double userToBestParkingDistance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), blockStartLoc.getLatitude(),
				blockStartLoc.getLongitude(), 'M');

		System.out.println("The Parking Block: " + parkingBlock + " allocated is located at a distance of: " + userToBestParkingDistance
				+ " from the user location");

		int totalTime = DistanceUtils.totalTime(userLoc.getLatitude(), userLoc.getLongitude(), blockStartLoc.getLatitude(),
				blockStartLoc.getLongitude(), 'M');
		System.out.println("Total Minutes to the parking lot in seconds: " + totalTime);
		TrialData returnTrialData = new TrialData(parkingBlock, totalTime, userLoc, blockStartLoc);
		return returnTrialData;
	}

	@Deprecated
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
				int totalAvailableParkingLots = GeneralUtils.getAvailableParkingLots(blockId, driverTimeStamp, 0);

				double gForce = gComp.getGForce(totalAvailableParkingLots, closestDistance);
				if (Integer.parseInt(rs.getString(10)) > 0) {
					mGForceDistance.put(Integer.parseInt(rs.getString(1)), gForce);
				}
			}

			int parkingBlock = GeneralUtils.getBlockWithMaxForce(mGForceDistance);
			// System.out.println("Block searched for driver at 2012-04-06:00:19:00 instant of time: "
			// + parkingBlock + " with a distance: " + temp
			// + " miles");

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
			date.setHours(12);
			date.setMinutes(00); // TODO Set the Seconds to 10
			date.setSeconds(00);
			long time = date.getTime();
			driverTimeStamp = new Timestamp(time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AppConstants.ALGORITHM_TYPE algorithmType = (AppConstants.ALGORITHM_TYPE) req.getAttribute(AppConstants.ALGORITHM_TYPE_KEY);
		switch (algorithmType) {
		case GRAVITATIONAL_DETERMINISTIC:
			ProbabilisticGravitationalImpl rSearch = new ProbabilisticGravitationalImpl();

			// Test: 37.806205, -122.424262

			// Note: Gravity is not good for UserLoc: 37.806464, -122.418864. It
			// should give me 546271, but gives 546281
			Location currentUserLoc = new Location(37.80348, -122.4115);

			// Initialize the driver timestamp to 2012-04-06:00:00:10:00
			rSearch.initializeDriverTime();

			// System.out.println("Computing your parking lot. Please wait ...");
			/*
			 * ---> Uncomment for Gravitational Algorithm using Road Network
			 */
			rSearch.computeGravityRoadNetwork(currentUserLoc, 0);
			break;
		case GREEDY_DETERMINISTIC:
			break;
		}
	}

	public static void main(String[] args) {
		ProbabilisticGravitationalImpl rSearch = new ProbabilisticGravitationalImpl();

		// Test: 37.806205, -122.424262

		// Note: Gravity is not good for UserLoc: 37.806464, -122.418864. It
		// should give me 546271, but gives 546281
		Location currentUserLoc = new Location(37.80348, -122.4115);

		// Initialize the driver timestamp to 2012-04-06:00:00:10:00
		rSearch.initializeDriverTime();

		// System.out.println("Computing your parking lot. Please wait ...");

		// rSearch.computeGravityFreeSpace(currentUserLoc); --> Uncomment for
		// Gravitation Algorithm using Free Space
		rSearch.computeGravityRoadNetwork(currentUserLoc, 0); // ---> Uncomment
																// for
																// Gravitational
																// Algorithm
																// using
																// Road Network

	}
}
