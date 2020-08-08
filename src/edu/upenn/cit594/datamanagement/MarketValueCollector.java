package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class MarketValueCollector implements PropertyValueCollector {
	private static HashMap<String, Double> ValInZipMap;
	private static HashMap<String, Integer> NumberInZipMap;

	public MarketValueCollector() {
		if (ValInZipMap == null) {
			ValInZipMap = new HashMap<String, Double>();
			NumberInZipMap = new HashMap<String, Integer>();
		}
	}
	@Override
	public double getTotalValue(String zipCode, List<Property> properties) {
		if (ValInZipMap.containsKey(zipCode)) {
			return ValInZipMap.get(zipCode);
		}
		double totalMarketValue = 0;
		int numberOfHouses = 0;
		for (Property prop : properties) {
			Double MarketValue = prop.getMarketValue();
			if (!MarketValue.isNaN()) {
				totalMarketValue = totalMarketValue + MarketValue;
				numberOfHouses = numberOfHouses + 1;
			}
		}
		ValInZipMap.put(zipCode, totalMarketValue);
		NumberInZipMap.put(zipCode, numberOfHouses);
		return totalMarketValue;
	}
	@Override
	public int getNumberOfValidProperties(String zipCode) {
		return NumberInZipMap.get(zipCode);
	}

}
