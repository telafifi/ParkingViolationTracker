package edu.upenn.cit594.ui;

import edu.upenn.cit594.datamanagement.*;

import java.util.*;
import java.util.regex.Pattern;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.logging.*;
import edu.upenn.cit594.processor.*;

public class UserInput {
	private Scanner in;
	private ViolationProcessor violationProcess;
	private PopulationReader populationReader;
	private PropertyProcessor propProcessor;
	private int totalPopulation;
	private String fineMap;
	private String commonFineOutput;
	private HashMap<String, Integer> avgMarketMap;
	private HashMap<String, Integer> avgLivableAreaMap;
	private HashMap<String, Integer> marketPerCapitaMap;
	private Logger logger;
	
	/**
	 * Constructor
	 * @param violationProcess
	 * @param populationReader
	 * @param propertyReader
	 */
	public UserInput(ViolationProcessor violationProcess, PopulationReader populationReader, PropertyProcessor propProcessor) {
		this.violationProcess = violationProcess;
		this.populationReader = populationReader;
		this.propProcessor = propProcessor;
		totalPopulation = 0;
		fineMap = "";
		commonFineOutput = "";
		logger = Logger.getInstance();
	}
	
	public void RunApplication() {
		in = new Scanner(System.in);
		ShowMenu();
		while (in.hasNextLine()) {
			String input = in.nextLine();
			logger.log(input); //log the input
			int instruction = ProcessInstruction(input);
			if (instruction >= 0) {
				switch (instruction) {
				case 0:
					System.out.println("Exiting program!");
					System.exit(0);
					break;
				case 1:
					PrintTotalPopulation();
					break;
				case 2:
					PrintFineMap();
					break;
				case 3:
					PrintAverageValue(3);
					break;
				case 4:
					PrintAverageValue(4);
					break;
				case 5:
					PrintTotalMarketValuePerCapita();
					break;
				case 6:
					PrintMostCommonFineMap();
					break;
				}
			}
			ShowMenu();
		}
		in.close();
	}
	
	/**
	 * Print out the menu of options to perform
	 */
	private void ShowMenu() {
		System.out.println("Please enter a number between 0 and 6 to perform the below actions:\n" + 
				"0: Exit the program\n" + 
				"1: Show total population for all zip codes available\n" + 
				"2: Show the total parking fines per capita for each zip code available\n" + 
				"3: Show the average market value for residences in a specified zip code\n" + 
				"4: Show the average total livable area for residences in a specified zip code\n" + 
				"5: Show the total residential market value per capita for a specified zip code\n" +
				"6: Show a sorted list of zip codes and their most common fine in that zip code. Values sorted by total livable area per capita");
	}
	
	/**
	 * Print the total population for all zip codes available
	 * Solve problem 1
	 */
	private void PrintTotalPopulation() {
		if (totalPopulation == 0) {
			totalPopulation = populationReader.GetTotalPopulation();
		}
		System.out.println(totalPopulation);
	}
	
	/**
	 * Process the instruction to check if it is valid
	 * @param input
	 * @return
	 */
	private int ProcessInstruction(String input) {
		if (InputFileChecks.isStringInteger(input)) {
			int inputVal = Integer.parseInt(input);
			if (inputVal >=0 && inputVal <= 6) {
				return inputVal;
			}
		}
		System.out.println("Please return a proper input. Input provided: " + input + ". Please input a from 0-6");
		return -1;
	}
	
	/**
	 * Show the total parking fines per capita for each zip code available
	 * Solve problem 2
	 */
	private void PrintFineMap() {
		if (fineMap.isEmpty()) {
			violationProcess.CalculateFinesPerCapita(populationReader.getPopulationMap());
			fineMap = violationProcess.getFineMap();
		}
		System.out.println(fineMap);
	}
	
	/**
	 * Show the average market value or livable area in a specified zip code
	 * Solve problem 3 or 4
	 * @param option
	 */
	private void PrintAverageValue(int option) {
		System.out.println("Please input a zip code:");
		if (in.hasNextLine()) { //read in the zipcode
			String zipCode = in.nextLine();
			logger.log(zipCode); //log the zip code
			int returnVal = 0;
			if (option == 3) {
				if (this.avgMarketMap == null) { //initialize the map if it is empty
					this.avgMarketMap = new HashMap<String, Integer>();
				}
				if (this.avgMarketMap.containsKey(zipCode)) { //memoization. check if this calculation has already run just return the value calculated previously
					returnVal = this.avgMarketMap.get(zipCode);
				}
				else {
					returnVal = propProcessor.getAverageValue(zipCode, new MarketValueCollector()); //calculate the average value
					this.avgMarketMap.put(zipCode, returnVal); //add it to the map
				}
			}
			else { //the input is either 3 or 4
				if (this.avgLivableAreaMap == null) { //initialize the map if it is empty
					this.avgLivableAreaMap = new HashMap<String, Integer>();
				}
				if (this.avgLivableAreaMap.containsKey(zipCode)) { //memoization. check if this calculation has already run just return the value calculated previously
					returnVal = this.avgLivableAreaMap.get(zipCode);
				}
				else {
					returnVal = propProcessor.getAverageValue(zipCode, new LivableAreaCollector()); //calculate the average value
					this.avgLivableAreaMap.put(zipCode, returnVal); //add it to the map
				}
			}
			System.out.println(returnVal);
		}
	}
	
	/**
	 * Show the total residential market value per capita for a specified zip code
	 * Solve problem 5
	 */
	private void PrintTotalMarketValuePerCapita() {
		System.out.println("Please input a zip code:");
		if (in.hasNextLine()) { //read in the zipcode
			String zipCode = in.nextLine();
			logger.log(zipCode); //log the zip code
			int returnVal = 0;
			if (this.marketPerCapitaMap == null) { //initialize the map if it is empty
				this.marketPerCapitaMap = new HashMap<String, Integer>();
			}
			if (this.marketPerCapitaMap.containsKey(zipCode)) { //memoization. check if this calculation has already run just return the value calculated previously
				returnVal = this.marketPerCapitaMap.get(zipCode);
			}
			else {
				returnVal = propProcessor.getTotalValuePerCapita(zipCode, populationReader.getPopulationMap(), new MarketValueCollector()); //calculate the total value per capita
				this.marketPerCapitaMap.put(zipCode, returnVal); //add it to the map
			}
			System.out.println(returnVal);
		}
	}
	
	/**
	 * Show a sorted list of zip codes and their most common fine in that zip code. These values are sorted by Total Livable Area per capita. Smallest shows up first
	 * Solve problem 6
	 */
	private void PrintMostCommonFineMap() {
		if (commonFineOutput.isEmpty()) { //if the string is empty, run the rocess to get the map
			ProcessMostCommonFineMap processMostCommon = new ProcessMostCommonFineMap(violationProcess, populationReader, propProcessor);
			commonFineOutput = processMostCommon.GetCommonFinesMap();
		}
		System.out.println(commonFineOutput); //print out map
	}
}
