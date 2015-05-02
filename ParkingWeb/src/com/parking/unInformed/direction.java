package com.parking.unInformed;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.parking.model.Location;

public class direction {
	  private static final String MAP_API = "https://maps.googleapis.com/maps/api";
	  
	  private static ArrayList<Location> directions= new ArrayList<Location>();	  
	  protected static  ArrayList<Location> run(String type, String format, ParameterNameValue[] params) throws Exception
	  {
		String query="";
	    StringBuilder locationBuilder = new StringBuilder(MAP_API +"/"+ type + "/" + format +"?");
	    for (int i = 0; i < params.length; i++)
	    {
	      if (i > 0)
	        locationBuilder.append('&');
	      locationBuilder.append(params[i].name).append('=').append(params[i].value);
	      if(params[i].name.equals("query"))
	      {
	    	  query=params[i].value;
	      }
	    }
	    String location = locationBuilder.toString();
	    System.out.println("URL:\n"+location);
	    URL url = new URL(location);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    HttpURLConnection.setFollowRedirects(true);
	    conn.setDoInput(true);
	    conn.connect();

	    int status = conn.getResponseCode();
	    while (true)
	    {
	      int wait = 0;
	      String header = conn.getHeaderField("Retry-After");
	      if (header != null)
	        wait = Integer.valueOf(header);
	      if (wait == 0)
	        break;
	      conn.disconnect();
	      Thread.sleep(wait * 10);
	      conn = (HttpURLConnection) new URL(location).openConnection();
	      conn.setDoInput(true);
	      conn.connect();
	      status = conn.getResponseCode();
	    }
	    
	    if (status == HttpURLConnection.HTTP_OK)
	    {
	      InputStream reader = conn.getInputStream();
	      URLConnection.guessContentTypeFromStream(reader);
	      StringBuilder builder = new StringBuilder();
	      int a = 0;
	      while ((a = reader.read()) != -1)
	      {
	        builder.append((char) a);
	      }
	      System.out.println("\nResponse:");
	      System.out.println(builder.toString());
	      directions=FetchDirection(builder.toString());
	    }
	    else
	    {
	    conn.disconnect();
	    }
		return directions;
	  }

	  public static void main(String[] args) throws Exception
	  {
		ArrayList<Location> intersection= new ArrayList<Location>();
	    intersection=run("directions","json", new ParameterNameValue[] {
			      new ParameterNameValue("origin", "37.802939, -122.418179"),
			      new ParameterNameValue("destination", "37.805227, -122.424519"),
			      new ParameterNameValue("sensor","false"),
			      new ParameterNameValue("key","AIzaSyAz1JAk83jhqzG85RObM3wTrlV6txGq3TM")
			    });
		  
	  }

	  protected static class ParameterNameValue
	  {
	    private final String name;
	    private final String value;

	    public ParameterNameValue(String name, String value)
	      throws UnsupportedEncodingException
	    {
	      this.name = URLEncoder.encode(name, "UTF-8");
	      this.value = URLEncoder.encode(value, "UTF-8");
	    }
	  }
	  protected static ArrayList<Location> FetchDirection(String a) throws IOException, JSONException
	  {
		  JSONParser parser = new JSONParser();
		    JSONObject response = null;
		    try {
		      response = (JSONObject) parser.parse(a);
		    } 
		    catch (ParseException pe) {
		      System.out.println("Error: could not parse JSON response:");
		      System.exit(1);
		    }
		   //System.out.println(response.toString()); 
		   JSONObject route=(JSONObject) ((JSONArray) response.get("routes")).get(0);
		   JSONObject leg=(JSONObject) ((JSONArray) route.get("legs")).get(0);
		   JSONArray steps = (JSONArray) (leg.get("steps"));
		   for(int i=0;i<steps.size();i++)
		   {
			   if(i==0)
			   {
			   String lattitude=((JSONObject) ((JSONObject) steps.get(i)).get("start_location")).get("lat").toString();
			   String longitude=((JSONObject) ((JSONObject) steps.get(i)).get("start_location")).get("lng").toString();
			   Location tempLoc=new Location(Double.parseDouble(lattitude),Double.parseDouble(longitude));
			   directions.add(tempLoc);
			   String gpscoor=lattitude+","+longitude;
			   System.out.println("Starting Co-ordinates: "+gpscoor);
			   
			   }
			   
			   String distance=((JSONObject) ((JSONObject) steps.get(i)).get("distance")).get("text").toString();
			   System.out.println("Distance: "+distance);
			   
			   String instruction=((JSONObject) steps.get(i)).get("html_instructions").toString();
			   System.out.println("Instructions: "+instruction);
			
			   try {
				String manuever = ((JSONObject) steps.get(i)).get("manuever").toString();
				System.out.println("Significatn Move: " + manuever);
			} catch (Exception e) {
				// TODO: handle exception
			}

			   String lattitude=((JSONObject) ((JSONObject) steps.get(i)).get("end_location")).get("lat").toString();
			   String longitude=((JSONObject) ((JSONObject) steps.get(i)).get("end_location")).get("lng").toString();
			   Location tempLoc=new Location(Double.parseDouble(lattitude),Double.parseDouble(longitude));
			   directions.add(tempLoc);
			   String gpscoor=lattitude+","+longitude;
			   if(i<steps.size()-1)
			   System.out.println("Next Co-ordinates: "+gpscoor);
			   else if(i==steps.size()-1)
			   System.out.println("Ending Co-ordinates: "+gpscoor);
		   }
		   
		   System.out.println("\nRoute Way Points:");
		   printPoints(directions);
		   return directions;
	  }

	private static void printPoints(ArrayList<Location> directions2) {

		for (int i=0;i<directions.size();i++)
			System.out.println(directions.get(i).getLatitude()+","+directions.get(i).getLongitude());
	}
	
	
	
	}
