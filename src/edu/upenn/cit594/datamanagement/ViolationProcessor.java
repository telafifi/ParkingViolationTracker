package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.*;
import java.util.List;

public abstract class ViolationProcessor {
	protected ViolationReader reader;
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public ViolationProcessor(String fileName) {
		reader = createReader(fileName);
	}
	
	/**
	 * Get list of violations
	 * @return
	 */
	public List<ParkingViolation> processViolations(){
		List<ParkingViolation> data = reader.readViolations();
		for (ParkingViolation violation : data) {
			System.out.println(violation.getDate()+ "\t" + violation.getFine()+ "\t" + violation.getDescription()+ "\t" + violation.getPlate_id()+ "\t" + violation.getState()+ "\t" + violation.getTicket_number()+ "\t" + violation.getZip_code());
		}
		return data;
	}
	
	/**
	 * class to create the reader
	 * @param fileName
	 * @return
	 */
	protected abstract ViolationReader createReader(String fileName);
}
