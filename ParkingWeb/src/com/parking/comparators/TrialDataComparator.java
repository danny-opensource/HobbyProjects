package com.parking.comparators;

import java.util.Comparator;

import com.parking.model.TrialData;

public class TrialDataComparator implements Comparator<TrialData> {

	@Override
	public int compare(TrialData o1, TrialData o2) {
		return o1.getTrianNumber() - o2.getTrianNumber();
	}

}
