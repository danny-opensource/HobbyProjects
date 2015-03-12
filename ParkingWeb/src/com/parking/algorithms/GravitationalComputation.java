package com.parking.algorithms;

public class GravitationalComputation {

	public double getGForce(final int numBlocks, final double distance) {
		return numBlocks / (Math.pow(distance, 2));
	}

}
