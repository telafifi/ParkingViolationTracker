package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;
import java.util.*;

public class PropertyProcessor {
	private Set<Property> properties;
	
	public PropertyProcessor(Set<Property> properties) {
		this.properties = properties;
	}
	
	public double getAverageValue(String zipCode, PropertyAverageValueCollector collect) {
		return collect.getAverageValue(zipCode, this.properties);
	}
}
