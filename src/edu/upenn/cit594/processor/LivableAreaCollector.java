package edu.upenn.cit594.processor;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class LivableAreaCollector implements PropertyValueCollector {
	//These values are static so that new Collectors can access the maps
	private static HashMap<String, Double> ValInZipMap; //map to hold the total livable area for each zip code
	private static HashMap<String, Integer> NumberInZipMap; //map to hold the number of properties that have valid livable area numbers in input file
	
	//constructor
	public LivableAreaCollector() {
		if (ValInZipMap == null) { //if the map is null, we need to instantiate both maps
			ValInZipMap = new HashMap<String, Double>();
			NumberInZipMap = new HashMap<String, Integer>();
		}
	}
	
	@Override
	public double getTotalValue(String zipCode, List<Property> properties) {
		if (ValInZipMap.containsKey(zipCode)) { //Memoization. check if the map already contains the zip code. If so then just return that value from the map
			return ValInZipMap.get(zipCode);
		}
		double totalLivableArea = 0;
		int numberOfHouses = 0;
		for (Property prop : properties) { //iterate through list of properties that contain the zip code
			Double LivableArea = prop.getTotalLivableArea(); //get the livable area for that property
			if (!LivableArea.isNaN()) { //if the livable area value is valid
				totalLivableArea = totalLivableArea + LivableArea; //increase the total for zip code
				numberOfHouses = numberOfHouses + 1; //increment valid house number
			}
		}
		ValInZipMap.put(zipCode, totalLivableArea); //put both values in the maps
		NumberInZipMap.put(zipCode, numberOfHouses);
		return totalLivableArea; //return the total value
	}

	@Override
	public int getNumberOfValidProperties(String zipCode) {  //Memoization. this method will only ever run AFTER getTotalValue runs
		return NumberInZipMap.get(zipCode);
	}
}
