package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.processor.*;

import java.util.*;

import edu.upenn.cit594.data.*;

public class Main {
	public static void main(String[] args) {
		
		PopulationReader myPop = new PopulationReader("population.txt");
		HashMap<String, Integer> populationMap = myPop.getPopulationMap();
//		
//		ViolationProcessor myProcess = new ViolationCSVProcessor("parking.csv");
//		myProcess.CalculateFinesPerCapita(myPop.getPopulationMap());
//		myProcess.PrintFineMap();
		
		
		
		System.out.println("Reading Data");
		PropertiesReader myReader = new PropertiesReader("properties.csv");
		List<Property> map = myReader.getPropertyList();
		PropertyProcessor myProcessor = new PropertyProcessor(map);
		Scanner in = new Scanner(System.in);
		System.out.println("Please input zipcode you would like to see");
		while (in.hasNextLine()) {
			String zipCode = in.nextLine();
			int val1 = myProcessor.getAverageValue(zipCode, new MarketValueCollector());
			System.out.println(zipCode + " " + val1);
			int val2 = myProcessor.getAverageValue(zipCode, new LivableAreaCollector());
			System.out.println(zipCode + " " + val2);
			int val3 = myProcessor.getTotalValuePerCapita(zipCode, populationMap, new MarketValueCollector());
			System.out.println(zipCode + " " + val3);
			System.out.println("Please input zipcode you would like to see");
			
		}
		
//		System.out.println(myPop.GetTotalPopulation());
	}
}
