package com.parking.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.parking.algorithms.GravitationalComputation;
import com.parking.model.UserLocation;
import com.parking.utils.DistanceUtils;
import com.parking.utils.GeneralUtils;

public class ResourceSearch {

	private List<Double> mClosestLatitudes;
	private List<Double> mClosestLongitudes;

	private Timestamp driverTimeStamp;

	private Hashtable<Integer, Double> mGForceDistance;

	private void computeShortestDistance(final UserLocation userLoc) {
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

				double gForce = gComp.getGForce(66, closestDistance); // TODO
																		// Remove
																		// 66.
				if (Integer.parseInt(rs.getString(10)) > 0) {
					mGForceDistance.put(Integer.parseInt(rs.getString(1)), gForce);
				}
			}

			int parkingBlock = GeneralUtils.getBlockWithMaxForce(mGForceDistance);
			System.out.println("Block searched for driver at 2012-04-06:00:00:00 instant of time: " + parkingBlock + " with a distance: " + temp
					+ " miles");

			// Scale: Driver travels 1 meter in 1 sec

			while (temp > 0) {
				temp = temp - 0.000621371; // Subracting 1 meter from mile

				// Increase 1 second from the timestamp
				driverTimeStamp.setSeconds(driverTimeStamp.getSeconds() + 1);

				System.out.println("driverTimeStamp is: " + driverTimeStamp);

				// Check for max time-stamp that is less than driverTimeStamp

			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	private void initializeDriverTime() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("06/04/2012");
			long time = date.getTime();
			driverTimeStamp = new Timestamp(time);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ResourceSearch rSearch = new ResourceSearch();

		// Test: 37.806180, -122.423803
		UserLocation currentUserLoc = new UserLocation(-122.423803, 37.806180);

		// Initialize the driver timestamp to 2012-04-06:00:00:00:00
		rSearch.initializeDriverTime();

		rSearch.computeShortestDistance(currentUserLoc);
	}
}
