package edu.upenn.cit594.datamanagement;

import java.util.List;

import edu.upenn.cit594.data.*;

public interface ViolationReader {
	public List<ParkingViolation> readViolations();
}
