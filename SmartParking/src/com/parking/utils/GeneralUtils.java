package com.parking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

import com.parking.model.Location;

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

	public static Location getLocation(final int node) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "cs440");
			stmt = conn.createStatement();
			String query = "select * from parking.\"node\" where node_id=" + node;

			rs = stmt.executeQuery(query);

			double latitude = 0;
			double longitude = 0;

			while (rs.next()) {
				latitude = Double.parseDouble(rs.getString(2));
				longitude = Double.parseDouble(rs.getString(3));
			}

			Location location = new Location(latitude, longitude);
			return location;

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			return null;
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
}
