package com.parking.unInformed;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class distance {
	  private static final String MAP_API = "https://maps.googleapis.com/maps/api";

	  protected static String[] run(String type, String format, Parameters[] params) throws Exception
	  {
		String query="";
		String [] result=new String[2];
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
	    System.out.println(location);
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
	      //System.out.println(builder.toString());
	      return(FetchDistanceTime(builder.toString()));

	    }
	    else
	    conn.disconnect();
		return result;
	  }

	  public static void main(String[] args) throws Exception
	  {
	   String abc[] = run("distancematrix","json", new Parameters[] {
			      new Parameters("origins", "901 S Ashland Ave, Chicago,IL"),
			      new Parameters("destinations", "Daley Library,Chicago, IL"),
			      new Parameters("sensor","false"),
			      new Parameters("key","AIzaSyAz1JAk83jhqzG85RObM3wTrlV6txGq3TM"),
			      new Parameters("units","imperial")
			    });
	   
	   System.out.println(abc[0].toString()+"\n"+abc[1].toString());
		  
	  }

	  protected static class Parameters
	  {
	    private final String name;
	    private final String value;

	    public Parameters(String name, String value)
	      throws UnsupportedEncodingException
	    {
	      this.name = URLEncoder.encode(name, "UTF-8");
	      this.value = URLEncoder.encode(value, "UTF-8");
	    }
	  }

	  protected static String[] FetchDistanceTime(String a) throws IOException, JSONException
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
//		   System.out.println(response.toString()); 
		   JSONObject elements=(JSONObject) ((JSONArray) response.get("rows")).get(0);
//		   System.out.println(elements.toString()); 
		   JSONObject element=(JSONObject) ((JSONArray) elements.get("elements")).get(0);
//		   System.out.println(element.toString());
		   JSONObject distance = (JSONObject) element.get("distance");
		   String dist = distance.get("value").toString();
		   JSONObject duration = (JSONObject) element.get("duration");
		   String time = duration.get("value").toString();
//		   System.out.println("Distance: "+dist);
//		   System.out.println("Time: "+time);
		   String [] disttime={dist,time};
		   return disttime;
	  }
	}
