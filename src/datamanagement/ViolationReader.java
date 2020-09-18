package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;

import edu.upenn.cit594.data.*;

public interface ViolationReader { //interface to read the parking violation file
	/**
	 * Return the list of parking violations from the input file
	 * @return
	 */
	public ArrayList<ParkingViolation> readViolations();
}
