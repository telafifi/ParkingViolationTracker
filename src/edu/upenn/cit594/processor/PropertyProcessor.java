package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;
import java.util.*;

public class PropertyProcessor {
	private List<Property> properties; //total list of properties
	private HashMap<String, List<Property>> propsInZipMap; //map to hold the list of properties within a map
	
	/**
	 * Constructor
	 * @param properties
	 */
	public PropertyProcessor(List<Property> properties) {
		this.properties = properties;
		this.propsInZipMap = new HashMap<String, List<Property>>(); //instantiate property map
	}
	
	/**
	 * Get the average value of the property specified by the collector
	 * @param zipCode
	 * @param collect
	 * @return
	 */
	public int getAverageValue(String zipCode, PropertyValueCollector collect) {
		List<Property> propertiesInZip = GetPropertiesInZipCode(zipCode); //get the list of properties in that zipcode
		if (propertiesInZip.isEmpty()) {
			return 0; //if there are no properties in that zip code then return 0
		}
		double totalValue = collect.getTotalValue(zipCode, propertiesInZip); //get the total value of the property held in the zip code
		int numberOfProperties = collect.getNumberOfValidProperties(zipCode); //get the number of valid properties
		return (int)(totalValue/numberOfProperties); //return an integer
	}
	
	/**
	 * Get the total value of the property per capita specified by the collector
	 * @param zipCode
	 * @param populationMap
	 * @param collect
	 * @return
	 */
	public int getTotalValuePerCapita(String zipCode, HashMap<String, Integer> populationMap, PropertyValueCollector collect) {
		if (!populationMap.containsKey(zipCode)) { //if the population map does not contain the value then return a zero
			return 0;
		}
		List<Property> propertiesInZip = GetPropertiesInZipCode(zipCode); //get the list of properties in that zipcode
		int population = populationMap.get(zipCode); //get population in that zip code
		if (propertiesInZip.isEmpty() || population == 0) {
			return 0; //if there are no properties in that zip code OR the population of that zip code is 0 then return 0
		}
		return (int)(collect.getTotalValue(zipCode, propertiesInZip)/population); //otherwise get the total value and divide by property in zip code
	}
	
	/**
	 * Get the list of properties within the zip code
	 * @param zipCode
	 * @return
	 */
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
