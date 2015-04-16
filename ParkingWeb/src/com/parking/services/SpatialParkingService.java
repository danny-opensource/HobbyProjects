package com.parking.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.parking.constants.AppConstants;
import com.parking.main.GravitationalImpl;
import com.parking.main.GreedyImpl;
import com.parking.model.Location;
import com.parking.model.TrialData;

/**
 * Core REST Service that services all the requests from the UI. Follows SOA
 * Based Architecure
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
@Path("/parking")
public class SpatialParkingService {
	@GET
	@Path("/gravitationaldeterministic/{trialCount}/{congestionLevel}")
	@Produces("application/xml")
	public Response applyGravitational(@PathParam("trialCount") String trialCount, @PathParam("congestionLevel") String congestionLevel) {
		System.out.println("CongestionLevel Received: " + congestionLevel);
		StringBuilder output = new StringBuilder("<trials>");
		int trials = Integer.parseInt(trialCount);
		GravitationalImpl gravityComp = new GravitationalImpl();
		for (int i = 0; i < trials; i++) {
			System.out.println("** Trials are being executed: " + i);
			Location sampleUserLoc = AppConstants.randomUserLocations.get(i);
			System.out.println("Location is: " + sampleUserLoc.toString());
			gravityComp.initializeDriverTime();
			TrialData trialData = gravityComp.computeGravityRoadNetwork(sampleUserLoc, Integer.parseInt(congestionLevel));
			trialData.setTrialNumber(i + 1);
			trialData.setCongestionLevel(Integer.parseInt(congestionLevel));
			AppConstants.sGravitationalTraialData.put(congestionLevel + "$" + (i + 1), trialData);
			double totalMins = (trialData.getTimeToParkingBlock() / (double) 60);
			output.append("<trial>");
			output.append("<number>" + (i + 1) + "</number>");
			output.append("<averageTime>" + totalMins + "</averageTime>");
			output.append("<userLocation>" + trialData.getUserLocation()+"</userLocation>");
			output.append("<blockLocation>" + trialData.getParkingBlockLocation() + "</blockLocation>");
			output.append("<congestionLevel>" + 0 + "</congestionLevel>");
			output.append("</trial>");
		}
		output.append("</trials>");
		return Response.status(200).entity(output.toString()).build();
	}

	@GET
	@Path("/greedydeterministic/{trialCount}/{congestionLevel}")
	@Produces("application/xml")
	public Response applyGreedy(@PathParam("trialCount") String trialCount, @PathParam("congestionLevel") String congestionLevel) {
		StringBuilder output = new StringBuilder("<trials>");
		int trials = Integer.parseInt(trialCount);
		GreedyImpl greedyImpl = new GreedyImpl();
		for (int i = 0; i < trials; i++) {
			System.out.println("** Trials are being executed: " + i);
			Location sampleUserLoc = AppConstants.randomUserLocations.get(i);
			System.out.println("Location is: " + sampleUserLoc.toString());
			greedyImpl.initializeDriverTime();
			int totalSecs = greedyImpl.computeGravityRoadNetwork(sampleUserLoc, Integer.parseInt(congestionLevel));
			double totalMins = (totalSecs / (double) 60);
			output.append("<trial>");
			output.append("<number>" + (i + 1) + "</number>");
			output.append("<averageTime>" + totalMins + "</averageTime>");
			output.append("<congestionLevel>" + 0 + "</congestionLevel>");
			output.append("</trial>");
		}
		output.append("</trials>");
		return Response.status(200).entity(output.toString()).build();
	}

}
