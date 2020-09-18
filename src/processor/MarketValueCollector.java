package edu.upenn.cit594.processor;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class MarketValueCollector implements PropertyValueCollector {
	//These values are static so that new Collectors can access the maps
	private static HashMap<String, Double> ValInZipMap; //map to hold the total livable area for each zip code
	private static HashMap<String, Integer> NumberInZipMap; //map to hold the number of properties that have valid livable area numbers in input file

	//constructor
	public MarketValueCollector() {
		if (ValInZipMap == null) { //if the map is null, we need to instantiate both maps
			ValInZipMap = new HashMap<String, Double>();
			NumberInZipMap = new HashMap<String, Integer>();
		}
	}
	@Override
	public double getTotalValue(String zipCode, List<Property> properties) {
		if (ValInZipMap.containsKey(zipCode)) {  //Memoization. check if the map already contains the zip code. If so then just return that value from the map
			return ValInZipMap.get(zipCode);
		}
		double totalMarketValue = 0;
		int numberOfHouses = 0;
		for (Property prop : properties) { //iterate through list of properties that contain the zip code
			Double MarketValue = prop.getMarketValue(); //get the market value for that property
			if (!MarketValue.isNaN()) { //if the market value is valid
				totalMarketValue = totalMarketValue + MarketValue; //increase the total for zip code
				numberOfHouses = numberOfHouses + 1; //increment valid house number
			}
		}
		ValInZipMap.put(zipCode, totalMarketValue); //put both values in the maps
		NumberInZipMap.put(zipCode, numberOfHouses);
		return totalMarketValue; //return the total value
	}
	@Override
	public int getNumberOfValidProperties(String zipCode) {  //Memoization. this method will only ever run AFTER getTotalValue runs
		return NumberInZipMap.get(zipCode);
	}

}
