package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.stream.*;

import edu.upenn.cit594.data.*;

public class AverageLivableAreaCollector implements PropertyAverageValueCollector {
	@Override
	public double getAverageValue(String zipCode, Set<Property> properties) {
		int numberOfHouses = 0;
		double totalLivableArea = 0;
		int numberSimilar = 0;
		for (Property prop : properties) {
			if (prop.getZipCode().equalsIgnoreCase(zipCode)) {
				numberSimilar = prop.getNumberOfSimilarProperties();
				Double LivableArea = prop.getTotalLivableArea();
				if (!LivableArea.isNaN()) {
					numberOfHouses = numberOfHouses + numberSimilar;
					totalLivableArea = totalLivableArea + numberSimilar * LivableArea;
				}
			}
			if (prop.getZipCode().compareTo(zipCode) > 1) { //this can be done because we are feeding it a sorted Set
				break;
			}
		}
		return totalLivableArea/numberOfHouses;
	}
}
