package processor;

import data.*;
import datamanagement.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public abstract class ViolationProcessor extends Thread { //extend thread to read all files simultaneously
	protected ViolationReader reader;
	private ArrayList<ParkingViolation> parkingViolations;
	private Map<String, Integer> populationMap;
	private TreeMap<String, Double> fineMap;
	private String fineMapOutput; //string to hold 
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public ViolationProcessor(String fileName) {
		reader = createReader(fileName); //read the file and get the list of violations
		this.fineMap = new TreeMap<String, Double>(); //instantiate tree of fines
		fineMapOutput = ""; //set finemap to null at processor launch
	}
	
	
	/**
	 * Used to read the file asynchronously
	 */
	public void run() {
		this.parkingViolations = reader.readViolations(); //read the violations at launch
	}
	
	/**
	 * Calculate the fines per capita by taking in a map of all zip codes and maps
	 * @return
	 */
	public void CalculateFinesPerCapita(Map<String, Integer> populationMap){ 
		this.populationMap = populationMap;
		this.PopulateFineTree();
		this.PopulateFinePerCapitaMap();
	}
	
	/**
	 * Populate the fine tree by going through every parking violation in the input file
	 */
	private void PopulateFineTree() {
		for (ParkingViolation p : parkingViolations) {
			if (p.getState().equalsIgnoreCase("PA")) { //check if the state is PA, otherwise go to next violation
				int fine = p.getFine(); 
				if (fine != 0) { //check if the fine is 0, otherwise go to next violation
					String zip_code = p.getZip_code(); //get the zip code of this violation
					if (!zip_code.isEmpty()) { //skip empty zipcodes
						AddFineToTree(zip_code, fine);
					}
				}
			}
		}
	}
	
	/**
	 * Calculate the fine per capita for each zip code
	 */
	private void PopulateFinePerCapitaMap() {
		for (String zip : fineMap.keySet()) { //go through the fine map
			Double fine = fineMap.get(zip); //get the total fine
			if (this.populationMap.containsKey(zip)) { //if the fines exist in the population map
				int population = populationMap.get(zip);
				if (population == 0) {
					fine = Double.NaN; //set fine to not a number to ignore it if the population is zero
				}
				else {
					fine = fine / population; //get the fine per capita
				}
			}
			else {
				fine = Double.NaN; //otherwise set as not a number
			}
			this.fineMap.put(zip, fine); //replace the value with the per capita value
		}
	}
	
	/**
	 * Print the fines per capita to the console for viewers to see (Memoization method to not loop through the list of inputs every time)
	 */
	public String getFineMap() {
		if (fineMapOutput.isEmpty()) {
			BuildFineMapOutput();
		}
		return fineMapOutput;
	}
	
	
	/**
	 * Build the fine map string to print by iterating through the entire list
	 */
	private void BuildFineMapOutput() {
		String errorString = "The population input file or parking violation input file provided is empty. Could not produce valid output without the inputs!";
		if (fineMap == null) { //return if the map is null or empty
			return;
		}
		else if (fineMap.isEmpty()) {
			fineMapOutput = errorString;
			return;
		}
		DecimalFormat df = new DecimalFormat("#.####"); //truncate the double to 4 decimal spots
		df.setRoundingMode(RoundingMode.DOWN); //set rounding mode to round down
		StringBuilder buildMapOutput = new StringBuilder(); //build a string builder
		for (String key : fineMap.keySet()) { //go through list of fines per capita
			Double finePerCapita = fineMap.get(key);
			if (!finePerCapita.isNaN() && finePerCapita != 0) { //check that the value is numeric
				buildMapOutput.append(key + " " + df.format(finePerCapita) + "\n"); //append that value in the correct format to the stringbuilder
			}
		}
		fineMapOutput = buildMapOutput.toString(); //store the values into the output string
		if (fineMapOutput.isEmpty()) {
			fineMapOutput = errorString;
		}
	}

	/**
	 * Get the current total fine in the tree for that zip code and increment it
	 * @param zip_code
	 * @param fine
	 */
	private void AddFineToTree(String zip_code, int fine) {
		double totalFine = 0.0;
		if (this.fineMap.containsKey(zip_code)) {
			totalFine = this.fineMap.get(zip_code); //if the map already contains the zipcode then extract that value
		}
		totalFine = totalFine + (double)fine; //increment total value by new fine
		this.fineMap.put(zip_code, totalFine); //add it to the map
	}
	
	/**
	 * Get list of parking violations
	 * @return
	 */
	public ArrayList<ParkingViolation> getParkingViolations() {
		return parkingViolations;
	}
	
	
	/**
	 * class to create the reader
	 * @param fileName
	 * @return
	 */
	protected abstract ViolationReader createReader(String fileName);
}
