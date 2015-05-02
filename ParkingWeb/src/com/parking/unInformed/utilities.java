package com.parking.unInformed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.parking.datastructure.distance;
import com.parking.model.RoadNetworkEdge;

public class utilities {
	
	public utilities()
	{};

	public static void printBlockData(ArrayList<RoadNetworkEdge> blocks) {
	
		for (int i=0;i<blocks.size();i++)
		{
			System.out.println(blocks.get(i).blockName+","+blocks.get(i).latitude1+","+blocks.get(i).longitude1);
		}
		}
	
	public static void printMap(String outputFile,ArrayList<RoadNetworkEdge> blocks) {
		try {
			File file = new File(outputFile);
			
			if(file.exists()){
				file.delete();
			}
			
			BufferedWriter output = new BufferedWriter(new FileWriter(file));		
			
			output.write("<html>\n"
					+"<head>\n"
			    +"<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n"
			    +"<script type=\"text/javascript\">\n"
			    +"google.load(\"visualization\", \"1\", {packages:[\"map\"]});\n"
			    +"google.setOnLoadCallback(drawChart);\n"
			    +"function drawChart(){\n"
			    +"var data = google.visualization.arrayToDataTable([\n"
			    +"['Lat', 'Long', 'Name'],\n");
			for (int i=0;i<blocks.size();i++)
			{
				output.write("["+blocks.get(i).latitude1+","+blocks.get(i).longitude1+",\'"+blocks.get(i).blockName+"\']");
				if(i!=blocks.size()-1)
				output.write(",\n");
			}
			    output.write("]);\n"
			    +"var map = new google.visualization.Map(document.getElementById('map_div'));\n"
			    +"map.draw(data, {showTip: true});\n"
			    +"}\n"
			    +"</script>\n"
			    +"</head>\n"
			    +"<body>\n"
			    +"<div id=\"info\" style=\"width: 100%; height: 50px\" background-color='black'>\n"
				+"<p></p>\n"
				+"<b><i>These are the blocks which are on the way to the destination</i></b>\n"
				+"&nbsp\n"
				+"<button id=\"myButton\" onclick=\"location.href = 'directionsMap"
				+4
				+".html';\">Suggest Block!!</button>"  
				+"&nbsp\n"
				+"</div>\n"
			    +"<div id=\"map_div\" style=\"width: 600px; height: 450px\"></div>\n"
			    +"</body>\n"
			    +"</html>\n");
			output.close();
		
		} catch (IOException e) {
			
			System.out.println("[Error] Output file write operation");
		}
	}
		
		public static void printDirectionsMap(int size,int i,String outputFile,String startPoint,String endPoint,double distance,double time,double totalTime) {
			try {
				File file = new File(outputFile);
				
				if(file.exists()){
					file.delete();
				}
				
				BufferedWriter output = new BufferedWriter(new FileWriter(file));		
				
				output.write("<!DOCTYPE html>\n"+
								"<html>\n"+
								"<head>\n"+
								"<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n"+
								"<meta charset=\"utf-8\">\n"+
								"<title>Directions service (complex)</title>\n"+
								"<style>\n"+
								"html, body, #map-canvas {\n"+
								"height: 100%;\n"+
								"margin: 0px;\n"+
								"padding: 0px"+
								"}\n"+
								"#panel {\n"+
								"position: absolute;\n"+
								"top: 5px;\n"+
								"left: 50%;\n"+
								"margin-left: -180px;\n"+
								"z-index: 5;\n"+
								"background-color: #fff;\n"+
								"padding: 5px;\n"+
								"border: 1px solid #999;\n"+
								"}\n"+
								"\");\n"+
								"</style>\n"+
							    "<script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true\"></script>\n"+
							    "<script>");
				
				output.write("var map; \n"+
				"var directionsDisplay; \n"+
				"var directionsService; \n"+
				"var stepDisplay;\n"+
				"var markerArray = [];\n"+

				"function initialize() {\n"+
				"// Instantiate a directions service.\n"+
				"directionsService = new google.maps.DirectionsService();\n"+

			 	"// Create a map and center it on Manhattan.\n"+
			 	"map = new google.maps.Map(document.getElementById('map-canvas'));\n"+

			 	"// Create a renderer for directions and bind it to the map.\n"+
			 	"var rendererOptions = {\n"+
			 	" map: map\n"+
			 	"}\n"+
			 	"directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions)\n"+

			  	"// Instantiate an info window to hold step text.\n"+
			  	" stepDisplay = new google.maps.InfoWindow();\n"+
			  	"}\n"+

			  	"function calcRoute() {\n"+

			  	"// Define start and end co-ordinates\n"+
			  	"var start = \""+startPoint+"\";\n"+
			  	"var end = \""+endPoint+"\";\n"+
			  	"var request = {\n"+
			  	"origin: start,\n"+
			  	"destination: end,\n"+
			  	"travelMode: google.maps.TravelMode.DRIVING\n"+
			  	"};\n"+

				"// Route the directions and pass the response to a\n"+
				"// function to create markers for each step.\n"+
				"directionsService.route(request, function(response, status) {\n"+
				"if (status == google.maps.DirectionsStatus.OK) {\n"+
				" var warnings = document.getElementById('warnings_panel');\n"+
				" warnings.innerHTML = '<b>' + response.routes[0].warnings + '</b>';\n"+
				" directionsDisplay.setDirections(response);\n"+
				"  showSteps(response);\n"+
				" }\n"+
				"});\n"+
				"}\n");

				output.write("function showSteps(directionResult) {\n"+
				  "// For each step, place a marker, and add the text to the marker's\n"+
				  "// info window. Also attach the marker to an array so we\n"+
				  "// can keep track of it and remove it when calculating new\n"+
				  "// routes.\n"+
				  "var myRoute = directionResult.routes[0].legs[0];\n"+

				  "for (var i = 0; i < myRoute.steps.length; i++) {\n"+
				  "var marker = new google.maps.Marker({\n"+
				  "position: myRoute.steps[i].start_location,\n"+
				  "map: map\n"+
				  "});\n"+
				  "attachInstructionText(marker, myRoute.steps[i].instructions);\n"+
				  "markerArray[i] = marker;\n"+
				  "}\n"+
				  "}\n"+

				"function attachInstructionText(marker, text) {\n"+
				"google.maps.event.addListener(marker, 'click', function() {\n"+
				"// Open an info window when the marker is clicked on,\n"+
				"// containing the text of the step.\n"+
				"stepDisplay.setContent(text);\n"+
				"stepDisplay.open(map, marker);\n"+
				"});\n"+
				"}\n"+

				"google.maps.event.addDomListener(window, 'load', initialize);\n"+
				"</script>\n"+
				"</head>\n"+
				   "<body onload=\"calcRoute();\">\n"+
				
					"<div id=\"info\" style=\"width: 100%; height: 50px\" background-color='black'>\n"
					+"<p></p>\n"	
					+"<b><i>This is the "+(size-i)
					+" nearest parking block on the way to the destination</i></b>"
					+"&nbsp");
				if(size!=i+1)
				output.write("<button id=\"myButton\" onclick=\"location.href = 'directionsMap"+(size-i-1)+".html';\">This is Full, Suggest next Block!!</button>"); 
				output.write("&nbsp"
					+"<b> Distance travelled:</b> <i>"+distance+" mts</i>\n"
					+"&nbsp\n"
					+"<b> Time taken:</b> <i>"+time+" seconds\n</i>"
					+"&nbsp"
					+"</div>"
				    +"<div id=\"map-canvas\"></div>\n"+
				    "&nbsp;\n"+
				    "<div id=\"warnings_panel\" style=\"width:100%;height:10%;text-align:center\"></div>\n"+
				  "</body>\n"+
				"</html>\n");
				output.close();
			
			} catch (IOException e) {
				
				System.out.println("[Error] Output file write operation");
			}
		
		
//		private static void blockOnWay(ArrayList<RoadNetworkEdge> blocks) {
//		OSMMaps maps = new OSMMaps();
//		distanceBetweenBlocks dbb= new distanceBetweenBlocks();
//		ArrayList<distanceBetweenBlocks> distanceArray = new ArrayList<distanceBetweenBlocks>();
//		for (int i=0;i<blocks.size();i++)
//		{
//			for(int j=0;j<blocks.size();j++)
//			{
//				double tempdst = Double.parseDouble(maps.getDistance(blocks.get(i).latitude1,blocks.get(i).longitude1,blocks.get(j).latitude1,blocks.get(j).longitude1));
//				dbb.setBlockId1(blocks.get(i).blockId);
//				dbb.setBlockId2(blocks.get(j).blockId);
//				dbb.setBlockName1(blocks.get(i).blockName);
//				dbb.setBlockName2(blocks.get(j).blockName);
//				dbb.setLatitude1(blocks.get(i).latitude1);
//				dbb.setLatitude2(blocks.get(j).latitude2);
//				dbb.setLongitude1(blocks.get(i).longitude1);
//				dbb.setLongitude2(blocks.get(j).longitude2);
//				dbb.setDistance(tempdst);
//			}
//			distanceArray.add(dbb);
//		}
//	}
	}

// To avoid the duplicates of blocks
		
		public static ArrayList<distance> addNode(ArrayList<distance> dstAll,ArrayList<distance> dst,distance temp)
		{
			if(dstAll.size()==0)
				dstAll.add(temp);
			else
				{
				int count=0;
				for(int i=0;i<dstAll.size();i++)
				{
					if(temp.blockId==dstAll.get(i).blockId)
						count++;
				}
				if(count==0)
					dstAll.add(temp);
				}
		return dst;			
		}

		public static ArrayList<distance> addNodes(ArrayList<distance> dstAll,distance temp)
		{
			if(dstAll.size()==0)
				dstAll.add(temp);
			else
				{
				int count=0;
				for(int i=0;i<dstAll.size();i++)
				{
					if(temp.blockId==dstAll.get(i).blockId)
						count++;
				}
				if(count==0)
					dstAll.add(temp);
				}
		return dstAll;			
		}

		public static void printData(ArrayList<distance> distAll) {

			for (int i=0;i<distAll.size();i++)
			{
				System.out.println(distAll.get(i).blockId+","+distAll.get(i).blockName+","+distAll.get(i).latitude+","+distAll.get(i).longitude+","
						+distAll.get(i).distance+distAll.get(i).time);
			}
			
		}
}
