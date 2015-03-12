package com.parking.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.parking.datastructure.Edge;
import com.parking.datastructure.Vertex;
import com.parking.model.Location;
import com.parking.utils.DatabaseUtils;

public class GreedyImpl {

	private Timestamp driverTimeStamp;

	private void initializeDriverTime() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("23/04/2012");
			date.setHours(18);
			date.setMinutes(00); // TODO Set the Seconds to 10
			date.setSeconds(00);
			long time = date.getTime();
			driverTimeStamp = new Timestamp(time);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void computeGravityRoadNetwork(final Location userLoc) {
		Connection conn = DatabaseUtils.getDBConnection();

		HashMap<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();

		Vertex vertex[] = new Vertex[1000];
		Edge edge[] = new Edge[1000];
		int index = 0;

		String query = "select * from parking.\"node\"";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int nodeId = 0;
			double latitude = 0.0;
			double longitude = 0.0;
			String nodeName = "";
			while (rs.next()) {
				nodeId = Integer.parseInt(rs.getString(1));
				latitude = Double.parseDouble(rs.getString(2));
				longitude = Double.parseDouble(rs.getString(3));
				nodeName = rs.getString(4);
				vertex[index].nodeId = nodeId;
				vertex[index].name = nodeName;
				vertex[index].latitude = latitude;
				vertex[index].longitude = longitude;
				vertexMap.put(nodeId, vertex[index]);
				index++;
			}
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		}

		index = 0;
		query = "select * from parking.\"edges\"";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int blockId = 0;
			String blockName = "";
			double latitude1 = 0.0;
			double longitude1 = 0.0;
			double latitude2 = 0.0;
			double longitude2 = 0.0;
			int node1 = 0;
			int node2 = 0;
			int numBlocks = 0;
			int operationalBlocks = 0;

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				blockId = Integer.parseInt(rs.getString(1));
				blockName = rs.getString(2);
				latitude1 = Double.parseDouble(rs.getString(3));
				longitude1 = Double.parseDouble(rs.getString(4));
				latitude2 = Double.parseDouble(rs.getString(5));
				longitude2 = Double.parseDouble(rs.getString(6));
				node1 = Integer.parseInt(rs.getString(7));
				node2 = Integer.parseInt(rs.getString(8));
				numBlocks = Integer.parseInt(rs.getString(9));
				operationalBlocks = Integer.parseInt(rs.getString(10));

				edge[index].blockId = blockId;
				edge[index].blockName = blockName;
				edge[index].latitude1 = latitude1;
				edge[index].longitude1 = longitude1;
				edge[index].latitude2 = latitude2;
				edge[index].node1 = node1;
				edge[index].node2 = node2;
				edge[index].numBlocks = numBlocks;
				edge[index].operationalBlocks = operationalBlocks;

			}

		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		}

	}

	public static void main(String[] args) {
		GreedyImpl greedyImpl = new GreedyImpl();

		Location currentUserLoc = new Location(37.805559, -122.414299);

		greedyImpl.computeGravityRoadNetwork(currentUserLoc);

	}

}
