package com.parking.datastructure;

/**
 * Road Vertex Data Structure
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class Vertex implements Comparable<Vertex> {
	public String name;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	public double latitude;
	public double longitude;
	public int nodeId;

	public Vertex(String argName) {
		name = argName;
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
	}
}
