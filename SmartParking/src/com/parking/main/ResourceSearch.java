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
import com.parking.model.Location;
import com.parking.utils.DistanceUtils;
import com.parking.utils.GeneralUtils;

public class ResourceSearch {

	private Timestamp driverTimeStamp;

	private Hashtable<Integer, Double> mGForceDistance;

	private boolean isParkingSpaceAvailable(final int block) {
		/*
		 * TODO Check if parking space is available for the block at the
		 * driverTimeStamp
		 */

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		try {
			String query = "select max(timestamp),block_id from parking.\"projection\" where block_id=" + block;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	private void computeGravityRoadNetwork(final Location userLoc) {

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

				int node1 = Integer.parseInt(rs.getString(7));
				int node2 = Integer.parseInt(rs.getString(8));

				Location node1Loc = GeneralUtils.getLocation(node1);
				Location node2Loc = GeneralUtils.getLocation(node2);

				double userToNode1Distance = 0;
				double userToNode2Distance = 0;
				double node1ToBlockDistance = 0;
				double node2ToBlockDistance = 0;

				double totalDistanceViaNode1 = 0;
				double totalDistanceViaNode2 = 0;

				userToNode1Distance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), node1Loc.getLatitude(),
						node1Loc.getLongitude(), 'M');

				userToNode2Distance = DistanceUtils.distance(userLoc.getLatitude(), userLoc.getLongitude(), node2Loc.getLatitude(),
						node2Loc.getLongitude(), 'M');

				node1ToBlockDistance = DistanceUtils.distance(node1Loc.getLatitude(), node1Loc.getLongitude(), Double.parseDouble(rs.getString(4)),
						Double.parseDouble(rs.getString(3)), 'M');

				node2ToBlockDistance = DistanceUtils.distance(node2Loc.getLatitude(), node2Loc.getLongitude(), Double.parseDouble(rs.getString(4)),
						Double.parseDouble(rs.getString(3)), 'M');

				totalDistanceViaNode1 = userToNode1Distance + node1ToBlockDistance;
				totalDistanceViaNode2 = userToNode2Distance + node2ToBlockDistance;

				double closestDistance = Math.min(totalDistanceViaNode1, totalDistanceViaNode2);

				if (temp > closestDistance) {
					temp = closestDistance;
				}

				System.out.println("Closest Distance: " + closestDistance);

				double gForce = gComp.getGForce(66, closestDistance); // TODO
																		// Remove
																		// 66.
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

				System.out.println("driverTimeStamp is: " + driverTimeStamp);

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

				System.out.println("Closest Distance: " + closestDistance);

				double gForce = gComp.getGForce(66, closestDistance); // TODO
																		// Remove
																		// 66.
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

				System.out.println("driverTimeStamp is: " + driverTimeStamp);

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

	private void initializeDriverTime() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("06/04/2012");
			long time = date.getTime();
			driverTimeStamp = new Timestamp(time);

			System.out.println(driverTimeStamp);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ResourceSearch rSearch = new ResourceSearch();

		// Test: 37.806180, -122.423803
		Location currentUserLoc = new Location(-122.423803, 37.806180);

		// Initialize the driver timestamp to 2012-04-06:00:00:00:00
		rSearch.initializeDriverTime();

		// rSearch.computeGravityFreeSpace(currentUserLoc);
		rSearch.computeGravityRoadNetwork(currentUserLoc);

	}
}
