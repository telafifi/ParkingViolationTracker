package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.data.*;

public class FinesPerCapitaCalculator {
	private ArrayList<ParkingViolation> parkingViolations;
	private HashMap<String, Integer> populationMap;
	private TreeMap<String, Double> fineMap;
	
	public FinesPerCapitaCalculator(ArrayList<ParkingViolation> parkingViolations, HashMap<String, Integer> populationMap) {
		this.parkingViolations = parkingViolations;
		this.populationMap = populationMap;
		this.fineMap = new TreeMap<String, Double>();
	}
	
	public void CalculateFinesPerCapita() {
		this.PopulateFineTree();
		
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
	 * Get the current total fine in the tree for that zip code and increment it
	 * @param zip_code
	 * @param fine
	 */
	private void AddFineToTree(String zip_code, int fine) {
		Double totalFine = this.fineMap.get(zip_code);
		if (totalFine == null) {
			totalFine = 0.0;
		}
		totalFine = totalFine + (double)fine;
		this.fineMap.put(zip_code, totalFine);
	}
	
}
