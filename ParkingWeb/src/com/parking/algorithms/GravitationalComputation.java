package com.parking.algorithms;

/**
 * Gravitation Algorithm Implementer
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class GravitationalComputation {

	public double getGForce(final int numBlocks, final double distance) {
		return numBlocks / (Math.pow(distance, 2));
	}
	
	public double getEstimatedGForce(final double probabilityBlocks, final double distance)
	{
		return probabilityBlocks / (Math.pow(distance, 2));
	}

}
