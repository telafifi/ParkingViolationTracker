package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class AverageLivableAreaCollector implements PropertyAverageValueCollector {
	private static HashMap<String, Integer> avgValInZipMap;

	public AverageLivableAreaCollector() {
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
		double totalLivableArea = 0;
		for (Property prop : properties) {
			Double LivableArea = prop.getTotalLivableArea();
			if (!LivableArea.isNaN()) {
				numberOfHouses = numberOfHouses + 1;
				totalLivableArea = totalLivableArea + LivableArea;
			}
		}
		Integer returnVal = (int)(totalLivableArea/numberOfHouses);
		avgValInZipMap.put(zipCode, returnVal);
		return returnVal;
	}
}
