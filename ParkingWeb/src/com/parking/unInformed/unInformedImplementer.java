package com.parking.unInformed;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.parking.constants.AppConstants;
import com.parking.datastructure.distance;
import com.parking.main.AppInitializer;
import com.parking.model.Location;
import com.parking.model.RoadNetworkEdge;
import com.parking.unInformed.direction.ParameterNameValue;
import com.parking.unInformed.distance.Parameters;
import com.parking.unInformed.utilities;

public class unInformedImplementer {

	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		
		//Debug Parameter '0' to run normal and '1' to debug
		int debug=0;
		
		//Radial distance parameter from destination
		double radius=0.1;
		
		//Perfection Parameter 'strict', '0' to run for little deviations on the way
		// '1' to run for no deviations from the route
		int strict=1;
		
		//Initializing the map file paths
		
		String presentWorkingDirectory = new java.io.File("").getAbsolutePath();
		String blocks = presentWorkingDirectory + "\\data\\" + "blocks"+ ".html";
		String nearBlocks = presentWorkingDirectory + "\\data\\"+ "nearBlocks" + ".html";
		String onTheWayBlocks = presentWorkingDirectory+ "\\data\\" + "onTheWayBlocks" + ".html";
		String directionsMap = presentWorkingDirectory+ "\\data\\" + "directionsMap";

		// ArrayList of all final distances
		ArrayList<distance> distAll=new ArrayList<distance>();
		
		// ArrayList to store the direction api returned intersections
		ArrayList<Location> intersection = new ArrayList<Location>();
		
		// ArrayList to store the blocks
		ArrayList<RoadNetworkEdge> Blocks = new ArrayList<RoadNetworkEdge>();
		ArrayList<RoadNetworkEdge> NearestBlocks = new ArrayList<RoadNetworkEdge>();
		ArrayList<RoadNetworkEdge> BlocksNearOnWay = new ArrayList<RoadNetworkEdge>();
		
		// Initializing the in-Memory data
		AppInitializer ai = new AppInitializer();
		
		try {
			ai.init();
		}
		catch (ServletException e) {
			e.printStackTrace();
		}
		
		double startLat=0;
		double startLong=0;
		double endLat=0;
		double endLong=0;
		double distance=0;
		double time = 0;

		// Random Start and End Points
		double randomLat1 = 37.805934;
		double randomLong1 = -122.411407;
		
		double randomLat2 = 37.808164;
		double randomLong2 = -122.416493;
		
		//Calling Google MAPS directions API and storing the turning intersections
		intersection = com.parking.unInformed.direction.run("directions","json",new ParameterNameValue[] {
						new ParameterNameValue("origin", Double.toString(randomLat1)+ ","+ Double.toString(randomLong1)),
						new ParameterNameValue("destination", Double.toString(randomLat2)+ ","+ Double.toString(randomLong2)),
						new ParameterNameValue("sensor", "false"),
						new ParameterNameValue("key","AIzaSyAz1JAk83jhqzG85RObM3wTrlV6txGq3TM") });

		System.out.println("\nStart Point:" + randomLat1 + "," + randomLong1);
		System.out.println("End Point:" + randomLat2 + "," + randomLong2 + "\n");
		System.out.println("\n ----Starting calculating the distances---- \n");
		
		// This variable is used to check constraint to add blocks only once 
		int runtime = 0;
		
		//Looping the Edges for each intersection
		for (int i = 0; i < intersection.size() - 1; i++) {
			ArrayList<distance> dist=new ArrayList<distance>();			
			Iterator<Integer> it = AppConstants.sInMemoryEdges.getKeySet().iterator();
			while (it.hasNext()) {
				
				RoadNetworkEdge edge = AppConstants.sInMemoryEdges.getEdge(it.next());
				
				double tempBlockLat1 = edge.latitude1;
				double tempBlockLong1 = edge.longitude1;
				double tempBlockLat2 = edge.latitude2;
				double tempBlockLong2 = edge.longitude2;
				double random1ToBlock = 0;
				double blockToRandom2 = 0;
				double random1ToBlock1 = 0;
				double block1ToRandom2 = 0;
				double random1ToRandom2 = 0;

				OSMMaps maps = new OSMMaps();
				
				double lat1 = intersection.get(i).getLatitude();
				double long1 = intersection.get(i).getLongitude();
				double lat2 = intersection.get(i + 1).getLatitude();
				double long2 = intersection.get(i + 1).getLongitude();

				
				random1ToBlock = Double.parseDouble(maps.getDistance(lat1,long1,tempBlockLat1,tempBlockLong1));
				blockToRandom2 = Double.parseDouble(maps.getDistance(tempBlockLat1,tempBlockLong1,lat2,long2));
				random1ToRandom2 = Double.parseDouble(maps.getDistance(lat1,long1,lat2,long2));
				if(strict==1)
				{
					random1ToBlock1=Double.parseDouble(maps.getDistance(lat1,long1,tempBlockLat2,tempBlockLong2));
					block1ToRandom2=Double.parseDouble(maps.getDistance(tempBlockLat2,tempBlockLong2,lat2,long2));
				}
					
				if(debug==1)
				{
				System.out.println("Block ID: " + edge.blockId+ " : "+ tempBlockLat1 + "," + tempBlockLong1);
				System.out.println("random1 to Block: " + random1ToBlock);
				System.out.println("Block to random2: " + blockToRandom2);
				System.out.println("random1 to random2: " + random1ToRandom2+ "\n");
				}

				if(strict==1 && debug==1)
				{
					System.out.println("random1 to Block"+random1ToBlock);
					System.out.println("Block to random2"+blockToRandom2);
				}
				
				if (random1ToBlock + blockToRandom2 <= random1ToRandom2 + 0.08
					&& random1ToBlock + blockToRandom2 >= random1ToRandom2 - 0.01
					&& random1ToBlock1+block1ToRandom2<=random1ToRandom2+0.08&&random1ToBlock1+block1ToRandom2>=random1ToRandom2-0.01
				) {
					//if the condition satisfied adding it to blocks
					Blocks.add(edge);
					//Assigning to distance instance to store block details and distance
					distance temp=new distance();
					temp.setBlockId(edge.blockId);
					temp.setBlockName(edge.blockName);
					temp.setLatitude(edge.latitude1);
					temp.setLongitude(edge.longitude1);
					temp.setDistance(random1ToBlock);

					//Adding the block only if it is alreaady not there
					//Avoid duplicates
					dist=utilities.addNode(distAll,dist,temp);
				}

				// This check is to find the blocks which are with in 0.1 miles of the destination
				if (runtime == 0)
				{
					if (blockToRandom2 <= radius) {
						NearestBlocks.add(edge);
					}
				}
			}

			runtime++;
			
			//Sorting the ArrayList based on distance parameter
			sortDistance(dist);
			
			//At the end of iteration add all the nodes to all blocks
			distAll.addAll(dist);
			
		}
		
		// Loop to find the blocks on the way and also with in radial distance
		for (RoadNetworkEdge item1 : Blocks) {
			for (RoadNetworkEdge item2 : NearestBlocks)
				if (item1.equals(item2))
					BlocksNearOnWay.add(item2);
		}

		System.out.println("\n Printing Blocks which are on the way \n");
		System.out.println("Before removing duplicates");
		utilities.printBlockData(Blocks);
		System.out.println("\n After removing duplicates");
		Blocks=removeDuplicates(Blocks);
		utilities.printBlockData(Blocks);
		System.out.println("\n Printing Blocks which are within "+radius+" mile \n");
		utilities.printBlockData(NearestBlocks);
		System.out.println("\n Printing Blokcs which are within "+radius+" mile and also which are on the way");
		utilities.printBlockData(BlocksNearOnWay);
		
		distAll.removeAll(distAll);
		for(int i=0;i<Blocks.size();i++)
		{
			startLat=randomLat1;
			startLong=randomLong1;
			endLat=Blocks.get(i).latitude1;
			endLong=Blocks.get(i).longitude1;
			
			String disttime[] = com.parking.unInformed.distance.run("distancematrix","json", new Parameters[] {
				      new Parameters("origins", startLat+","+startLong),
				      new Parameters("destinations", endLat+","+endLong),
				      new Parameters("sensor","false"),
				      new Parameters("key","AIzaSyAz1JAk83jhqzG85RObM3wTrlV6txGq3TM"),
				      new Parameters("units","imperial")
				    });
			distance=Double.parseDouble(disttime[0]);
			time=Double.parseDouble(disttime[1]);
			
//			if(disttime[0].contains("mi"))
//			{
//				String text = disttime[0];
//		        Pattern pattern = Pattern.compile("([^s]*)\\smi");
//		        Matcher matcher = pattern.matcher(text);
//		        while(matcher.find()) {
//		        	distance = Double.parseDouble(matcher.group(1));
//		        	System.out.println("Text:"+text+"Distance:"+distance);
//		        }
//			}
//			else if(disttime[0].contains("ft"))
//			{
//				String text = disttime[0];
//		        Pattern pattern = Pattern.compile("([^s]*)\\smi");
//		        Matcher matcher = pattern.matcher(text);
//		        while(matcher.find()) {
//		        	distance = Double.parseDouble(matcher.group(1))*0.000189394;
//		        	System.out.println("Text:"+text+"Distance:"+distance);
//		        }
//			}
			
			
//			if(disttime[1].contains("min"))
//			{
//				String text = disttime[0];
//		        Pattern pattern = Pattern.compile("([^s]*)\\smin");
//		        Matcher matcher = pattern.matcher(text);
//		        while(matcher.find()) {
//		        	time = Double.parseDouble(matcher.group(1));
//		        }
//			}
//			else if(disttime[1].contains("sec"))
//			{
//				String text = disttime[0];
//		        Pattern pattern = Pattern.compile("([^s]*)\\ssec");
//		        Matcher matcher = pattern.matcher(text);
//		        while(matcher.find()) {
//		        	time = Double.parseDouble(matcher.group(1))*0.0166667;
//		        }
//			}
			
			distance temp=new distance();
			temp.setBlockId(Blocks.get(i).blockId);
			temp.setBlockName(Blocks.get(i).blockName);
			temp.setLatitude(Blocks.get(i).latitude1);
			temp.setLongitude(Blocks.get(i).longitude1);
			temp.setDistance(distance);
			temp.setTime(time);
			distAll=utilities.addNodes(distAll,temp);
		}
		sortDistance(distAll);
		utilities.printMap(blocks, Blocks);
		utilities.printMap(nearBlocks, NearestBlocks);
		utilities.printMap(onTheWayBlocks, BlocksNearOnWay);
		// blockOnWay(Blocks);
		//utilities.printDirectionsMap(directionsMap, randomLat1+","+randomLong1,randomLat2+","+randomLong2);
		
		System.out.println("Sorted Output");
		//utilities.printDirectionsMap(directionsMap+".html",randomLat1+","+randomLong1,distAll.get(distAll.size()-4).getLatitude()+","+distAll.get(distAll.size()-4).getLongitude());
		utilities.printData(distAll);
		
//		OSMMaps maps = new OSMMaps();
		
		double parseTime=0.0;
		double totalTime=0.0;
		
		for(int i=distAll.size()-4;i<distAll.size();i++)
		{
		if(i==distAll.size()-4)
		{
			startLat=randomLat1;
			startLong=randomLong1;
			endLat=distAll.get(i).getLatitude();
			endLong=distAll.get(i).getLongitude();
		
		}
		
		else
		{
			startLat=distAll.get(i-1).getLatitude();
			startLong=distAll.get(i-1).getLongitude();
			endLat=distAll.get(i).getLatitude();
			endLong=distAll.get(i).getLongitude();
			}
		//distance = Double.parseDouble(maps.getDistance(startLat,startLong,endLat,endLong));
		String disttime[] = com.parking.unInformed.distance.run("distancematrix","json", new Parameters[] {
			      new Parameters("origins", startLat+","+startLong),
			      new Parameters("destinations", endLat+","+endLong),
			      new Parameters("sensor","false"),
			      new Parameters("key","AIzaSyAz1JAk83jhqzG85RObM3wTrlV6txGq3TM"),
			      new Parameters("units","imperial")
			    });
		System.out.println(startLat+","+startLong);
		System.out.println(endLat+","+endLong);
		System.out.println(distAll.get(i).blockId);
		System.out.println(distAll.get(i).blockName);		
		System.out.println("Distance: "+disttime[0]);
		System.out.println("Time: "+disttime[1]);
		distance=Double.parseDouble(disttime[0]);
//		time=Double.parseDouble(disttime[1]);
		time=distance/5;
		totalTime+=time;
		utilities.printDirectionsMap(distAll.size(),i,directionsMap+(distAll.size()-i)+".html",startLat+","+startLong,endLat+","+endLong,distance,time,totalTime);
		
//		if((disttime[1].indexOf("min"))!=-1)
//		totalTime+=Double.parseDouble(disttime[1].substring(0,disttime[1].indexOf("min")-1));
//		System.out.println("Time:"+Double.parseDouble(disttime[1].substring(0,disttime[1].indexOf("min")-1)));
		}
		
		System.out.println("Total Time to park: "+totalTime);
	}
	
	//Sort Method to sort blocks based on distance
	public static void sortDistance(ArrayList<distance> dist){		
		
	    Collections.sort(dist, new distanceComparator());
	}
    
	//Sort Method with comparator
	public static class distanceComparator implements Comparator<distance> {
		public int  compare(distance d1, distance d2) {
  	    	return Double.compare(d1.getDistance(), d2.getDistance());
  	    }
  	}
	
	public static ArrayList<RoadNetworkEdge> removeDuplicates(ArrayList<RoadNetworkEdge> Blocks)
	{
		ArrayList<RoadNetworkEdge> l1 = Blocks;
		ArrayList<RoadNetworkEdge> l2 = new ArrayList <RoadNetworkEdge>();
			
		if(l2.size()==0)
			l2.add(l1.get(0));
		
		       for(int i=0;i<l1.size();i++)
		        {
		    	   for(int j=0;j<l2.size();j++)
		        	   if(!l2.contains(l1.get(i))) 
		        		   l2.add(l1.get(i));
		        }
		return(l2);
	}
}