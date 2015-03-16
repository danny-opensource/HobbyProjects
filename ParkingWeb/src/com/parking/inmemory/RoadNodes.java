package com.parking.inmemory;

import java.util.HashMap;
import java.util.Set;

import com.parking.model.RoadNetworkNode;

public class RoadNodes {

	private HashMap<Integer, RoadNetworkNode> mInMemoryNodes;

	public RoadNodes() {
		mInMemoryNodes = new HashMap<Integer, RoadNetworkNode>();
	}

	public void insert(int nodeId, RoadNetworkNode node) {
		mInMemoryNodes.put(nodeId, node);
	}

	public RoadNetworkNode getNode(int nodeId) {
		return mInMemoryNodes.get(nodeId);
	}

	public Set<Integer> getKeySet() {
		return mInMemoryNodes.keySet();
	}

}