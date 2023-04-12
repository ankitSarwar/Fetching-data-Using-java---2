package com.geeksterAssignment.location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.apache.logging.log4j.util.Strings.join;

@SpringBootApplication
public class LocationApplication {

	public static void main(String[] args) throws Exception{
//		SpringApplication.run(LocationApplication.class, args);

		URL getUrl =new URL("https://api.zippopotam.us/us/33162");
		HttpURLConnection connection=(HttpURLConnection) getUrl.openConnection();
		connection.setRequestMethod("GET");

		int responseCode = connection.getResponseCode();

		if(responseCode==200){

			BufferedReader in =new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder jsonResponseData = new StringBuilder();
			String readLine = null;


			while ((readLine = in.readLine()) != null) {
				jsonResponseData.append(readLine);
			}

			in.close();
			JSONObject masterData = new JSONObject(jsonResponseData.toString());

			System.out.println("country name " + masterData.get("country"));
			System.out.println("country abbreviation " + masterData.get("country abbreviation"));

			JSONArray placesArray = masterData.getJSONArray("places"); // Convert places to JSONArray
			for (int i = 0; i < placesArray.length(); i++) {
				JSONObject placeObject = placesArray.getJSONObject(i); // Get the ith place object
				String placeName = placeObject.getString("place name");
				String longitude = placeObject.getString("longitude");
				String state = placeObject.getString("state");
				String stateAbbr = placeObject.getString("state abbreviation");
				String latitude = placeObject.getString("latitude");
				System.out.println(placeName +"  "+longitude+"  "+state+"  "+stateAbbr+"  " +latitude);
			}
		} else {
			System.out.println("This is not valid URL- " + responseCode);
		}
	}

}
