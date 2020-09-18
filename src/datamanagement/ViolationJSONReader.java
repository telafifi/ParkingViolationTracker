package datamanagement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import data.*;
import logging.Logger;

public class ViolationJSONReader implements ViolationReader {
	private String fileName;
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public ViolationJSONReader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public ArrayList<ParkingViolation> readViolations() {
		ArrayList<ParkingViolation> violations = new ArrayList<ParkingViolation>(); //instantiate list of parking violations
		JSONParser parser = new JSONParser(); //launch JSON parser
		try {
			Logger logger = Logger.getInstance();
			logger.log(this.fileName); //log the time and filename that has been read
			
			//get the array of JSON objects from file
			JSONArray jsonViolations = (JSONArray)parser.parse(new FileReader(fileName)); //get array of Json objects
			
			//use an iterator to iterate over each element of the array
			Iterator iter = jsonViolations.iterator();
			
			//iterate while there are more objects in array
			while (iter.hasNext()) {
				JSONObject violation = (JSONObject) iter.next();
				
				int ticket_number = ((Long)violation.get("ticket_number")).intValue();
				String plate_id = (String)violation.get("plate_id");
				String date = (String)violation.get("date");
				String zip_code = (String)violation.get("zip_code");
				String violationDescription = (String)violation.get("violation");
				int fine = ((Long)violation.get("fine")).intValue();
				String state = (String)violation.get("state");
				
				//Create parking violation object
				ParkingViolation parkingViolation = new ParkingViolation(ticket_number, plate_id, date, zip_code, violationDescription, fine, state);
				
				//add to list
				violations.add(parkingViolation);
				
			}
		}
		catch (Exception e) {
			System.out.println("The parking violation file input does not exist or cannot be read. Please verify and try again.");
		}
		return violations;
	}
}
