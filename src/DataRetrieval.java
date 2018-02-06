package com.teksystem.RestClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.*;

public class DataRetrieval {

  public static void main(String[] args) {
	try {

		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://services.groupkt.com/state/get/USA/all");

		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		String output = response.getEntity(String.class);

		System.out.println("Output from Server .... \n");
		//System.out.println(output);
		
	
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(output);
		
		JSONObject jsonData= (JSONObject) jsonObj.get("RestResponse");
		
		System.out.println(jsonData.get("result"));
		JSONArray arrayData= (JSONArray) jsonData.get("result"); 
		
		System.out.println(arrayData.size());
		
		//User Input
		System.out.println("Please enter state name or state abbreviation: ");
		Scanner input=new Scanner(System.in);
	   String abbrOrName="";
while ((abbrOrName=input.next()) != "exit"){
	System.out.println("You entered is : "+abbrOrName);
	  
	   //replace _ with space
	    if(abbrOrName.contains("_")){
	    	abbrOrName=abbrOrName.replaceAll("_", " ");
	    	
	    }
	   // System.out.println("You entered is : "+abbrOrName);
		
		Iterator iterator = arrayData.iterator();
        while (iterator.hasNext()) {
           // System.out.println(iterator.next());
        	JSONObject jsonObj1 = (JSONObject) iterator.next();
        	
        	if(jsonObj1.get("name").equals(abbrOrName) || jsonObj1.get("abbr").equals(abbrOrName)){
        		System.out.println("largest city is "+jsonObj1.get("largest_city"));
        		System.out.println( "capital is "+jsonObj1.get("capital"));
        	}
        	
            //System.out.println(jsonObj1.get("name"));
            //System.out.println(jsonObj1.get("capital"));
           // System.out.println(jsonObj1.get("abbr"));
           // System.out.println(jsonObj1.get("largest_city"));
           // System.out.println("===================================================");
        }
        
        System.out.println("Please enter state name or state abbreviation: ");
}		
		
		

	  } catch (Exception e) {

		e.printStackTrace();

	  }

	}
}
