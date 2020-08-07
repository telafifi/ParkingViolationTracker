package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public abstract class ViolationProcessor {
	protected ViolationReader reader;
	private ArrayList<ParkingViolation> parkingViolations;
	private HashMap<String, Integer> populationMap;
	private TreeMap<String, Double> fineMap;
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public ViolationProcessor(String fileName) {
		reader = createReader(fileName);
		this.parkingViolations = reader.readViolations(); //read the violations at launch
		this.fineMap = new TreeMap<String, Double>();
	}
	
	/**
	 * Calculate the fines per capita
	 * @return
	 */
	public void CalculateFinesPerCapita(HashMap<String, Integer> populationMap){
		this.populationMap = populationMap;
		this.PopulateFineTree();
		this.CalculateFinePerCapita();
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
	
	private void CalculateFinePerCapita() {
		for (String zip : fineMap.keySet()) {
			Double fine = fineMap.get(zip);
			if (this.populationMap.containsKey(zip)) {
				int population = populationMap.get(zip);
				fine = fine / population;
			}
			else {
				fine = Double.NaN;
			}
			this.fineMap.put(zip, fine);
		}
	}
	
	public TreeMap<String, Double> getFineMap() {
		return fineMap;
	}
	
	public void PrintFineMap() {
		if (fineMap == null || fineMap.isEmpty()) {
			return;
		}
		DecimalFormat df = new DecimalFormat("#.####"); //truncate the double to 4 decimal spots
		df.setRoundingMode(RoundingMode.DOWN);
		for (String key : fineMap.keySet()) {
			Double finePerCapita = fineMap.get(key);
			if (!finePerCapita.isNaN()) {
				System.out.println(key + " " + df.format(finePerCapita));
			}
			
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
			totalFine = this.fineMap.get(zip_code);
		}
		totalFine = totalFine + (double)fine;
		this.fineMap.put(zip_code, totalFine);
	}
	
	/**
	 * class to create the reader
	 * @param fileName
	 * @return
	 */
	protected abstract ViolationReader createReader(String fileName);
}
