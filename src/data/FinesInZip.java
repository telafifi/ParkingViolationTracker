package edu.upenn.cit594.data;

import java.util.*;

public class FinesInZip implements Comparable {
	private String zipCode;
	private Integer livableAreaPerCapita;
	private HashMap<String, Integer> fineTypeMap;
	
	public FinesInZip(String zipCode, int livableAreaPerCapita) {
		this.zipCode = zipCode;
		this.livableAreaPerCapita = livableAreaPerCapita;
		this.fineTypeMap = new HashMap<String, Integer>();
	}
	

	public HashMap<String, Integer> getFineTypeMap() {
		return fineTypeMap;
	}
	
	/**
	 * Update the fine map with a new fine type
	 * @param fineDescription
	 */
	public void UpdateFineMap(String fineDescription) {
		int number = 1;
		if (fineTypeMap.containsKey(fineDescription)) { //increment value in map if it already contains the key
			number = fineTypeMap.get(fineDescription) + 1;
		}
		fineTypeMap.put(fineDescription,  number); //update the fine map
	}
	
	/**
	 * Return the most common fine in the map
	 * @return
	 */
	public String getMostCommonFine() {
		if (fineTypeMap.isEmpty()) {
			return "No fines in this zip code"; //return error message if the string is empty
		}
		int maximumNum = 0;
		String mostCommon = "";
		for (String fineDescription : fineTypeMap.keySet()) { //iterate through keys
			int fineCount = fineTypeMap.get(fineDescription);
			if (fineCount >= maximumNum) { //if the finecount is greater than previous maximum, then store those values
				maximumNum = fineCount;
				mostCommon = fineDescription;
			}
		}
		return mostCommon;
	}


	public void setFineTypeMap(HashMap<String, Integer> fineTypeMap) {
		this.fineTypeMap = fineTypeMap;
	}


	public String getZipCode() {
		return zipCode;
	}


	public Integer getLivableAreaPerCapita() {
		return livableAreaPerCapita;
	}


	/**
	 * Compare the objects by first comparing the livable area per capita and then the zipcodes in case those are equal
	 */
	@Override
	public int compareTo(Object otherProperty) {
		FinesInZip myOtherProperty = (FinesInZip)otherProperty;
		int returnVal = livableAreaPerCapita.compareTo(myOtherProperty.getLivableAreaPerCapita()); //compare total livable area per capita
		if (returnVal == 0) {
			returnVal = zipCode.compareTo(myOtherProperty.getZipCode()); //if livable area per capita compare zip codes
		}
		return returnVal;
	}
}
