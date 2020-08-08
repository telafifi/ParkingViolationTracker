package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class LivableAreaCollector implements PropertyValueCollector {
	private static HashMap<String, Double> ValInZipMap;
	private static HashMap<String, Integer> NumberInZipMap;
	
	public LivableAreaCollector() {
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
		double totalLivableArea = 0;
		int numberOfHouses = 0;
		for (Property prop : properties) {
			Double LivableArea = prop.getTotalLivableArea();
			if (!LivableArea.isNaN()) {
				totalLivableArea = totalLivableArea + LivableArea;
				numberOfHouses = numberOfHouses + 1;
			}
		}
		ValInZipMap.put(zipCode, totalLivableArea);
		NumberInZipMap.put(zipCode, numberOfHouses);
		return totalLivableArea;
	}

	@Override
	public int getNumberOfValidProperties(String zipCode) {
		return NumberInZipMap.get(zipCode);
	}
}
