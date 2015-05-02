package com.parking.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.parking.constants.AppConstants;
import com.parking.utils.DistanceUtils;

public class CachePreProcessor {

	
	

	public static void main(String[] args) {
		double totalDistance = DistanceUtils.distance(37.795752,-122.39434, 37.8066485617,-122.4124854154, 'M');
		double totalTime = DistanceUtils.totalTime(37.8077921947,-122.4194870247, 37.800136,-122.398633, 'M');
		
		System.out.println("Total Distance: " + totalDistance);
		System.out.println("Total Time: " + totalTime);

	}
}
