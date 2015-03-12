package com.parking.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.parking.constants.AppConstants;
import com.parking.main.GravitationalImpl;
import com.parking.model.Location;

@Path("/hello")
public class GravitationalService {
	@GET
	@Path("/gravitationaldeterministic/{param}")
	@Produces("application/xml")
	public Response applyGravitational(@PathParam("param") String trialCount) {
		StringBuilder output = new StringBuilder("<trials>");

		int trials = Integer.parseInt(trialCount);
		GravitationalImpl gravityComp = new GravitationalImpl();
		for (int i = 0; i < trials; i++) {
			Location sampleUserLoc = AppConstants.randomUserLocations.get(i);
			gravityComp.initializeDriverTime();
			int totalMins = gravityComp.computeGravityRoadNetwork(sampleUserLoc);
			output.append("<trial>");
			output.append("<number>" +  + i + 1 + "</number>");
			output.append("<averageTime>" + totalMins + "</averageTime>");
			output.append("</trial>");
		}
		output.append("</trials>");
		return Response.status(200).entity(output.toString()).build();
	}

}
