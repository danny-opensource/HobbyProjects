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

}
