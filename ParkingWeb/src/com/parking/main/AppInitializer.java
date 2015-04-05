package com.parking.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.parking.constants.AppConstants;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.model.RoadNetworkNode;

public class AppInitializer extends HttpServlet {

	private void loadUserLocations() {

	}

	@Override
	public void init() throws ServletException {

		System.out.println("---- Initializing the User Locations ------");
		AppConstants.randomUserLocations = new HashMap<Integer, Location>();
		AppConstants.randomUserLocations.put(0, new Location(37.806205, -122.424262));
		AppConstants.randomUserLocations.put(1, new Location(37.805082, -122.421966));
		AppConstants.randomUserLocations.put(2, new Location(37.804700, -122.421097));
		AppConstants.randomUserLocations.put(3, new Location(37.806464, -122.419091));
		AppConstants.randomUserLocations.put(4, new Location(37.805836, -122.419606));
		AppConstants.randomUserLocations.put(5, new Location(37.805590, -122.417857));
		AppConstants.randomUserLocations.put(6, new Location(37.807015, -122.417964));
		AppConstants.randomUserLocations.put(7, new Location(37.804412, -122.422996));
		AppConstants.randomUserLocations.put(8, new Location(37.805531, -122.422041));
		AppConstants.randomUserLocations.put(9, new Location(37.804751, -122.420743));
		AppConstants.randomUserLocations.put(10, new Location(37.804277, -122.421773));
		AppConstants.randomUserLocations.put(11, new Location(37.804929, -122.419391));
		AppConstants.randomUserLocations.put(12, new Location(37.803683, -122.420850));

		AppConstants.randomUserLocations.put(13, new Location(37.805853, -122.419370));
		AppConstants.randomUserLocations.put(14, new Location(37.804912, -122.419391));
		AppConstants.randomUserLocations.put(15, new Location(37.804785, -122.425195));
		AppConstants.randomUserLocations.put(16, new Location(37.805133, -122.417975));
		AppConstants.randomUserLocations.put(17, new Location(37.803988, -122.419316));
		AppConstants.randomUserLocations.put(18, new Location(37.805616, -122.417878));
		AppConstants.randomUserLocations.put(19, new Location(37.804709, -122.420990));
		AppConstants.randomUserLocations.put(20, new Location(37.805819, -122.419767));
		AppConstants.randomUserLocations.put(21, new Location(37.803539, -122.419949));
		AppConstants.randomUserLocations.put(22, new Location(37.804904, -122.419316));
		AppConstants.randomUserLocations.put(23, new Location(37.806557, -122.421430));
		AppConstants.randomUserLocations.put(24, new Location(37.804438, -122.422953));
		AppConstants.randomUserLocations.put(25, new Location(37.804124, -122.418061));

		AppConstants.randomUserLocations.put(26, new Location(37.805302, -122.416194));
		AppConstants.randomUserLocations.put(27, new Location(37.804378, -122.416054));
		AppConstants.randomUserLocations.put(28, new Location(37.805362, -122.415765));
		AppConstants.randomUserLocations.put(29, new Location(37.805056, -122.421945));
		AppConstants.randomUserLocations.put(30, new Location(37.804921, -122.419305));
		AppConstants.randomUserLocations.put(31, new Location(37.804802, -122.412889));
		AppConstants.randomUserLocations.put(32, new Location(37.803937, -122.412203));
		AppConstants.randomUserLocations.put(33, new Location(37.803573, -122.422363));
		AppConstants.randomUserLocations.put(34, new Location(37.802284, -122.417320));
		AppConstants.randomUserLocations.put(35, new Location(37.803471, -122.415743));
		AppConstants.randomUserLocations.put(36, new Location(37.804260, -122.415057));
		AppConstants.randomUserLocations.put(37, new Location(37.804633, -122.414070));
		AppConstants.randomUserLocations.put(38, new Location(37.805751, -122.420400));

		AppConstants.randomUserLocations.put(39, new Location(37.804217, -122.421762));
		AppConstants.randomUserLocations.put(40, new Location(37.804005, -122.416645));
		AppConstants.randomUserLocations.put(41, new Location(37.803030, -122.414788));
		AppConstants.randomUserLocations.put(42, new Location(37.804565, -122.414241));
		AppConstants.randomUserLocations.put(43, new Location(37.804192, -122.417760));
		AppConstants.randomUserLocations.put(44, new Location(37.806243, -122.416344));
		AppConstants.randomUserLocations.put(45, new Location(37.803293, -122.417052));
		AppConstants.randomUserLocations.put(46, new Location(37.804031, -122.426311));
		AppConstants.randomUserLocations.put(47, new Location(37.802123, -122.418554));
		AppConstants.randomUserLocations.put(48, new Location(37.803234, -122.417503));
		AppConstants.randomUserLocations.put(49, new Location(37.802564, -122.422760));
		AppConstants.randomUserLocations.put(50, new Location(37.803912, -122.418340));
		AppConstants.randomUserLocations.put(51, new Location(37.802488, -122.416044));

		AppConstants.randomUserLocations.put(52, new Location(37.802310, -122.424487));
		AppConstants.randomUserLocations.put(53, new Location(37.801106, -122.421097));
		AppConstants.randomUserLocations.put(54, new Location(37.802742, -122.424756));
		AppConstants.randomUserLocations.put(55, new Location(37.803302, -122.418211));
		AppConstants.randomUserLocations.put(56, new Location(37.804285, -122.417074));
		AppConstants.randomUserLocations.put(57, new Location(37.803064, -122.418801));
		AppConstants.randomUserLocations.put(58, new Location(37.806726, -122.412943));
		AppConstants.randomUserLocations.put(59, new Location(37.804870, -122.412428));
		AppConstants.randomUserLocations.put(60, new Location(37.805082, -122.410658));
		AppConstants.randomUserLocations.put(61, new Location(37.805751, -122.412750));
		AppConstants.randomUserLocations.put(62, new Location(37.806625, -122.413265));
		AppConstants.randomUserLocations.put(63, new Location(37.805090, -122.411838));
		AppConstants.randomUserLocations.put(64, new Location(37.804082, -122.413308));

		initInMemoryData();
	}

	private void initInMemoryData() {
		System.out.println("*** initializing InMemory DataStructures ... ");
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
			String query = "select * from parking.\"node\"";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			RoadNetworkNode node;
			while (rs.next()) {
				node = new RoadNetworkNode();
				node.nodeId = Integer.parseInt(rs.getString(1));
				System.out.println("NodeID in AppInitializer is : " + AppConstants.sInMemoryNodes);
				node.latitude = Double.parseDouble(rs.getString(2));
				node.longitude = Double.parseDouble(rs.getString(3));
				node.nodeName = rs.getString(4);
				AppConstants.sInMemoryNodes.insert(node.nodeId, node);
			}

			System.out.println("*** Initializing InMemory Edges ... ");
			query = "select * from parking.\"edges\" where block_id <> -1";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			RoadNetworkEdge edge;
			while (rs.next()) {
				edge = new RoadNetworkEdge();
				edge.blockId = Integer.parseInt(rs.getString(1));
				edge.blockName = rs.getString(2);
				edge.latitude1 = Double.parseDouble(rs.getString(3));
				edge.longitude1 = Double.parseDouble(rs.getString(4));
				edge.latitude2 = Double.parseDouble(rs.getString(5));
				edge.longitude2 = Double.parseDouble(rs.getString(6));
				edge.nodeId1 = Integer.parseInt(rs.getString(7));
				edge.nodeId2 = Integer.parseInt(rs.getString(8));
				edge.numBlocks = Integer.parseInt(rs.getString(9));
				edge.numOperational = Integer.parseInt(rs.getString(10));
				AppConstants.sInMemoryEdges.insert(edge.blockId, edge);
			}

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				System.out.println("In AppInitializer. Exception in closing connections!");
				ex.printStackTrace();
			}
		}
	}
}
