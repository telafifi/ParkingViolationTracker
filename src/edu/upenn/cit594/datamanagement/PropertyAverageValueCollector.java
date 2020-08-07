package edu.upenn.cit594.datamanagement;

import java.util.*;
import edu.upenn.cit594.data.*;

public interface PropertyAverageValueCollector {
	public int getAverageValue(String zipCode, List<Property> properties);
}
