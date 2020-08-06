package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import edu.upenn.cit594.data.*;

public class ViolationCSVReader implements ViolationReader {
	private String fileName;
	BufferedReader fileReader;
	
	public ViolationCSVReader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public List<ParkingViolation> readViolations() {
		List<ParkingViolation> violations = new ArrayList<ParkingViolation>();
		File file = new File(this.fileName);
		try {
			fileReader = new BufferedReader(new FileReader(file)); //buffered reader to read file
			String line;
			while ((line = fileReader.readLine()) != null) {
				String[] lineComponents = line.split(","); //maybe come back to split using regex in csvparser
				String date = lineComponents[0];
				int fine = Integer.parseInt(lineComponents[1]);
				String violationDescription = lineComponents[2];
				String plate_id = lineComponents[3];
				String state = lineComponents[4];
				int ticket_number = Integer.parseInt(lineComponents[5]);
				String zip_code = "";
				if (lineComponents.length == 7) {
					zip_code = lineComponents[6];
				}
				
				//Create parking violation object
				ParkingViolation parkingViolation = new ParkingViolation(ticket_number, plate_id, date, zip_code, violationDescription, fine, state);
				
				//add to list
				violations.add(parkingViolation);
				
			}
			fileReader.close(); //close the file reader
		}
		catch (Exception e) {
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
