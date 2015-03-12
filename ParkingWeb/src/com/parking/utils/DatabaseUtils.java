package com.parking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

	private static Connection sDatabaseConnection;

	/*
	 * Return a Singleton Database Connection Object
	 */
	public static Connection getDBConnection() {
		if (sDatabaseConnection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
				return null;
			}

			try {
				sDatabaseConnection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "cs440");
				return sDatabaseConnection;
			} catch (SQLException sqlE) {
				sqlE.printStackTrace();
				return null;
			}
		}
		return sDatabaseConnection;
	}
}
