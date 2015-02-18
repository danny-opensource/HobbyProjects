package com.parking.datastructure;

public class Edge {

	public Vertex target;
	public double weight;
	
	public int blockId = 0;
	public String blockName = "";
	public double latitude1 = 0.0;
	public double longitude1 = 0.0;
	public double latitude2 = 0.0;
	public double longitude2 = 0.0;
	public int node1 = 0;
	public int node2 = 0;
	public int numBlocks = 0;
	public int operationalBlocks = 0;

	public Edge(Vertex argTarget, double argWeight) {
		target = argTarget;
		weight = argWeight;
	}

}
