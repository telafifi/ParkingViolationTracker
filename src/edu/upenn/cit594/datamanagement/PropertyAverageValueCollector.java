package edu.upenn.cit594.datamanagement;

import java.util.*;
import edu.upenn.cit594.data.*;

public interface PropertyAverageValueCollector {
	public double getAverageValue(String zipCode, Set<Property> properties);
}
