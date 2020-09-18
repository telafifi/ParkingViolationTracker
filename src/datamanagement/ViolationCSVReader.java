package datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import data.*;
import logging.Logger;

public class ViolationCSVReader implements ViolationReader {
	private String fileName;
	BufferedReader fileReader;
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public ViolationCSVReader(String fileName) {
		this.fileName = fileName;
	}
	
	
	@Override
	public ArrayList<ParkingViolation> readViolations() {
		ArrayList<ParkingViolation> violations = new ArrayList<ParkingViolation>(); //instantiate list
		File file = new File(this.fileName); //create file object
		try {
			Logger logger = Logger.getInstance();
			logger.log(this.fileName); //log the time and filename that has been read
			fileReader = new BufferedReader(new FileReader(file)); //buffered reader to read file
			String line;
			while ((line = fileReader.readLine()) != null) { //go through file
				String[] lineComponents = line.split(","); //split each line at the comma
				if (lineComponents.length >= 6) { //check if the string has atleast 6 components
					String date = lineComponents[0];
					int fine = 0;
					if (InputFileChecks.isStringInteger(lineComponents[1])) { //check if the value can be converted to an int, otherwise set to 0
						fine = Integer.parseInt(lineComponents[1]);
					}
					String violationDescription = lineComponents[2];
					String plate_id = lineComponents[3];
					String state = lineComponents[4];
					int ticket_number = 0;
					if (InputFileChecks.isStringInteger(lineComponents[5])) { //check if the value can be converted to an int, otherwise set to 0
						ticket_number = Integer.parseInt(lineComponents[5]);
					}
					String zip_code = "";
					if (lineComponents.length == 7) {
						zip_code = lineComponents[6]; //if a zip code is included in line item then include it in the object
					}
					
					//Create parking violation object
					ParkingViolation parkingViolation = new ParkingViolation(ticket_number, plate_id, date, zip_code, violationDescription, fine, state);
					
					//add to list
					violations.add(parkingViolation);
					
				}
			}
		}
		catch (Exception e) { //if reading the file runs into any error
			System.out.println("The parking violation file input does not exist or cannot be read. Please verify and try again.");
		}
		finally {
			if (fileReader != null){
				try {
					fileReader.close(); //close the file reader
				} catch (IOException e) {
					System.out.println("Population file could not be closed");
				} 
			}
		}
		return violations;
	}

}
