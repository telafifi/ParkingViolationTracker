package edu.upenn.cit594.datamanagement;

import java.util.*;
import edu.upenn.cit594.data.*;

public interface PropertyValueCollector {
	public double getTotalValue(String zipCode, List<Property> properties);
	public int getNumberOfValidProperties(String zipCode);
}
