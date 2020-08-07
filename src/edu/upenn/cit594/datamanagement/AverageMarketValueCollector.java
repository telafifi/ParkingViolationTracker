package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class AverageMarketValueCollector implements PropertyAverageValueCollector {
	private static HashMap<String, Integer> avgValInZipMap;

	public AverageMarketValueCollector() {
		if (avgValInZipMap == null) {
			avgValInZipMap = new HashMap<String, Integer>();
		}
	}
	@Override
	public int getAverageValue(String zipCode, List<Property> properties) {
		if (avgValInZipMap.containsKey(zipCode)) {
			return avgValInZipMap.get(zipCode);
		}
		int numberOfHouses = 0;
		double totalMarketValue = 0;
		for (Property prop : properties) {
			Double MarketValue = prop.getMarketValue();
			if (!MarketValue.isNaN()) {
				numberOfHouses = numberOfHouses + 1;
				totalMarketValue = totalMarketValue + MarketValue;
			}
		}
		Integer returnVal = (int)(totalMarketValue/numberOfHouses);
		avgValInZipMap.put(zipCode, returnVal);
		return returnVal;
	}

}
