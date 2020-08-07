package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;

import edu.upenn.cit594.data.*;

public interface ViolationReader {
	public ArrayList<ParkingViolation> readViolations();
}
