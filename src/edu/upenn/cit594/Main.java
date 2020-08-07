package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.processor.*;

import java.util.*;

import edu.upenn.cit594.data.*;

public class Main {
	public static void main(String[] args) {
		
//		PopulationReader myPop = new PopulationReader("population.txt");
//		
//		
//		ViolationProcessor myProcess = new ViolationCSVProcessor("parking.csv");
//		myProcess.CalculateFinesPerCapita(myPop.getPopulationMap());
//		myProcess.PrintFineMap();
		
		
		
		System.out.println("start");
		PropertiesReader myReader = new PropertiesReader("properties.csv");
		TreeMap<Property, Integer> map = myReader.getPropertyMap();
		PropertyProcessor myProcessor = new PropertyProcessor(map.keySet());
		String zipCode = "19154";
		double val1 = myProcessor.getAverageValue(zipCode, new AverageMarketValueCollector());
		System.out.println(zipCode + " " + val1);
		double val2 = myProcessor.getAverageValue(zipCode, new AverageLivableAreaCollector());
		System.out.println(zipCode + " " + val2);
		
//		System.out.println(myPop.GetTotalPopulation());
	}
}
