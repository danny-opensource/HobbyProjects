package com.parking.maps;

import com.parking.utils.DistanceUtils;

public class TestBed {
	public static void main(String[] args) {
		double anotherDistVal = DistanceUtils.anotherDistance(37.806205, -122.424262,37.803293, -122.417202, 'M');
		double googleDistance = DistanceUtils.distance(37.806205, -122.424262, 37.803293, -122.417202, 'M');

		System.out.println("Another Distance: " + anotherDistVal);
		System.out.println("Google Distance: " + googleDistance);
	}
}
