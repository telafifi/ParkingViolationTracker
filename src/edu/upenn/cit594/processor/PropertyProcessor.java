package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;
import java.util.*;

public class PropertyProcessor {
	private List<Property> properties;
	private HashMap<String, List<Property>> propsInZipMap;
	
	public PropertyProcessor(List<Property> properties) {
		this.properties = properties;
		this.propsInZipMap = new HashMap<String, List<Property>>();
	}
	
	public int getAverageValue(String zipCode, PropertyValueCollector collect) {
		List<Property> propertiesInZip = GetPropertiesInZipCode(zipCode);
		double totalValue = collect.getTotalValue(zipCode, propertiesInZip);
		int numberOfProperties = collect.getNumberOfValidProperties(zipCode);
		return (int)(totalValue/numberOfProperties);
	}
	
	public int getTotalValuePerCapita(String zipCode, HashMap<String, Integer> populationMap, PropertyValueCollector collect) {
		if (!populationMap.containsKey(zipCode)) {
			return 0;
		}
		return (int)(collect.getTotalValue(zipCode, GetPropertiesInZipCode(zipCode))/populationMap.get(zipCode));
	}
	
	private List<Property> GetPropertiesInZipCode(String zipCode){
		if (propsInZipMap.containsKey(zipCode)) { //memoization. Return previous list if zipcode had been called previously
			return propsInZipMap.get(zipCode);
		}
		ArrayList<Property> propsInZip = new ArrayList<Property>();
		for (Property prop : properties) { //go through list of properties and add properties in that zip code to the list
			if (prop.getZipCode().equalsIgnoreCase(zipCode)) {
				propsInZip.add(prop);
			}
		}
		this.propsInZipMap.put(zipCode, propsInZip); //add to the map;
		return propsInZip;
	}
	
}
