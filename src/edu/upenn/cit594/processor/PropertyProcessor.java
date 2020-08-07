package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;
import java.util.*;

public class PropertyProcessor {
	private List<Property> properties;
	private HashMap<String, List<Property>> propsInZipMap;
	private HashMap<String, Double> avgMarketInZipMap;
	private HashMap<String, Double> avgLivableInZipMap;
	
	public PropertyProcessor(List<Property> properties) {
		this.properties = properties;
		this.propsInZipMap = new HashMap<String, List<Property>>();
		this.avgLivableInZipMap = new HashMap<String, Double>();
		this.avgLivableInZipMap = new HashMap<String, Double>();
	}
	
	public int getAverageValue(String zipCode, PropertyAverageValueCollector collect) {
		return collect.getAverageValue(zipCode, GetPropertiesInZipCode(zipCode));
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
