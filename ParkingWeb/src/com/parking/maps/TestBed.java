package com.parking.maps;

import java.math.BigDecimal;

import com.parking.utils.DistanceUtils;

public class TestBed {
	public static void main(String[] args) {
		double anotherDistVal = DistanceUtils.anotherDistance(37.806205, -122.424262,37.803293, -122.417202, 'M');
	/*	double googleDistance = DistanceUtils.distance(37.806205, -122.424262, 37.803293, -122.417202, 'M');

		//System.out.println("Another Distance: " + anotherDistVal);
		//System.out.println("Google Distance: " + googleDistance);
		
		double totalDistance = DistanceUtils.distance(37.806205, -122.424262, 37.803293, -122.417202, 'M');
		double totalTime = DistanceUtils.totalTime(37.806205, -122.424262, 37.803293, -122.417202, 'M');
		System.out.println("Total Distance: " + totalDistance);
		System.out.println("Total Time: " + totalTime);*/
		
		int x = 10;
		double y = x/(double)100;
		System.out.println(y);
	}
}
