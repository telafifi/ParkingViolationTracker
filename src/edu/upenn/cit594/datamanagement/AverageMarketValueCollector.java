package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class AverageMarketValueCollector implements PropertyAverageValueCollector {

	@Override
	public double getAverageValue(String zipCode, Set<Property> properties) {
		int numberOfHouses = 0;
		double totalMarketValue = 0;
		int numberSimilar = 0;
		for (Property prop : properties) {
			if (prop.getZipCode().equalsIgnoreCase(zipCode)) {
				numberSimilar = prop.getNumberOfSimilarProperties();
				Double MarketValue = prop.getMarketValue();
				if (!MarketValue.isNaN()) {
					numberOfHouses = numberOfHouses + numberSimilar;
					totalMarketValue = totalMarketValue + numberSimilar * prop.getMarketValue();
				}
			}
			if (prop.getZipCode().compareTo(zipCode) > 1) {
				break;
			}
		}
		return totalMarketValue/numberOfHouses;
	}

}
