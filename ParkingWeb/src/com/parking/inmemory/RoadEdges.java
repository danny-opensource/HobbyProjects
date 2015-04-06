package com.parking.inmemory;

import java.util.HashMap;
import java.util.Set;

import com.parking.model.RoadNetworkEdge;

/**
 * Road Edges Map Construction
 * 
 * @author Madan Gopal (Git: madan1988)
 *
 */
public class RoadEdges {

	private HashMap<Integer, RoadNetworkEdge> mInMemoryEdges;

	public RoadEdges() {
		mInMemoryEdges = new HashMap<Integer, RoadNetworkEdge>();
	}

	public void insert(int blockId, RoadNetworkEdge edge) {
		mInMemoryEdges.put(blockId, edge);
	}

	public RoadNetworkEdge getEdge(int blockId) {
		return mInMemoryEdges.get(blockId);
	}

	public Set<Integer> getKeySet() {
		return mInMemoryEdges.keySet();
	}

}
