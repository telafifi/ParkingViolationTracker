package datamanagement;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import data.*;
import logging.Logger;

public class PropertiesReader {
	private String fileName;
	private int zipCodeColumn; //column number that holds zip code
	private int marketValueColumn; //column number that holds market value
	private int livableAreaColumn; //column number that holds livable area
	private int totalNumFields; //total number of header fields
	private List<Property> propertyList; //holds the property
	BufferedReader fileReader;
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public PropertiesReader(String fileName) {
		this.fileName = fileName;
		propertyList = new ArrayList<Property>(); //instatiate list
	}
	
	
	/**
	 * Read the property file and store the list of inputs in the propertyList
	 */
	public void ReadPropertyFile() {
		File file = new File(this.fileName); //instantiate the file
		try {
			Logger logger = Logger.getInstance();
			logger.log(this.fileName); //log the time and filename that has been read
			fileReader = new BufferedReader(new FileReader(file)); //buffered reader to read file
			String line = fileReader.readLine(); //first line of a properly formatted file would have a header
			if (line == null) { //close the file and end reading if there is no header file
				fileReader.close();
				return;
			}
			GetRequiredColumnLocations(line.split(",")); //get the locations of the correct headers
			
			while ((line = fileReader.readLine()) != null) {
				String[] lineComponents = line.split(","); //try splitting the line with commas
				if (lineComponents.length != this.totalNumFields) { //if the total number of components of that line does not match the total number of header fields
					lineComponents = ParseCSVFile(line); //this method takes a while to run so only run it on lines that need it
					/*
					 * parse the line using regex to not split lines at commas between quotations. This is a very time intensive process
					 * Minimize this by evaluating this split lazily, only if the split using commas doesn't match the number of headers
					 */
				}
				String zipCode = AdjustZipCode(GetLineComponent(this.zipCodeColumn, lineComponents)); //get 5 character zip code
				Double marketValue = GetNumericComponent(GetLineComponent(this.marketValueColumn, lineComponents)); //check if number is numeric and get that value
				Double totalLivableArea = GetNumericComponent(GetLineComponent(this.livableAreaColumn, lineComponents)); //check if number is numeric and get that value
				Property currentProp = new Property(zipCode, marketValue, totalLivableArea); //create property value
				propertyList.add(currentProp);
			}
		}
		catch (Exception e) { //if reading the file runs into any error
			System.out.println("The population file input does not exist or cannot be read. Please verify and try again");
		}
		finally {
			if (fileReader != null){
				try {
					fileReader.close(); //close the file reader
				} catch (IOException e) {
					System.out.println("Population file could not be closed");
				} 
			}
		}
	}
	
	/**
	 * Parse a csv line item but ignore commas if the line contains commas between quotes
	 * @param line
	 * @return
	 */
	private String[] ParseCSVFile(String line) {
		//This method splits on a comma ONLY if that comma has zero, or an even number of quotes ahead of it
		String otherThanQuote = " [^\"] "; //splitting using this regex is time intensive
        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"; //this regex checks for commas and checks to see if there is a quotation mark after the comma. 
        //If so, it will not parse the next comma until a second quotation mark is found
        return line.split(regex, -1);
	}
	
	/**
	 * Search the header row for locations of zip_code, market_value, and total_livable_area column locations
	 * @param headerComponents
	 */
	private void GetRequiredColumnLocations(String[] headerComponents) {
		this.totalNumFields = headerComponents.length; //set total length
		for (int i = 0; i < totalNumFields; i++) { //go through list of headers to find the 3 required headers
			String header = headerComponents[i];
			if (header.equalsIgnoreCase("market_value")) {
				this.marketValueColumn = i;
			}
			else if (header.equalsIgnoreCase("total_livable_area")) {
				this.livableAreaColumn = i;
			}
			else if (header.equalsIgnoreCase("zip_code")) {
				this.zipCodeColumn = i;
			}
		}
	}
	
	/**
	 * Get the value in that line in that specific column
	 * @param column
	 * @param lineComponents
	 * @return
	 */
	private String GetLineComponent(int column, String[] lineComponents) {
		if (column < lineComponents.length) { //this is a check if the column is empty and at the end of the line, this would cause the split to be short
			return lineComponents[column];
		}
		return "";
	}
	
	/**
	 * if the length of the zipcode is larger than 5 numbers then only grab the first five characters 
	 * @param zipCode
	 */
	private String AdjustZipCode(String zipCode) {
		if (zipCode.length() > 5) {
			zipCode = zipCode.substring(0, 5); //No need to return a string since the object will be modified
		}
		return zipCode;
	}
	
	/**
	 * Get the numerical value of the input. If the input is not a number return NaN
	 * @param lineComponent
	 * @return
	 */
	private double GetNumericComponent(String lineComponent) {
		if (Pattern.matches("^\\d+\\.?\\d*$", lineComponent)) { //check if the string is a valid decimal number
			double returnVal = Double.parseDouble(lineComponent); //parse the string
			return returnVal;
		}
		else {
			return Double.NaN; //if the value could not be parsed then return a Not a number value
		}
	}
	
	/**
	 * Return the property list. If the list is empty, read the file and populate the list prior to returning
	 * @return
	 */
	public List<Property> getPropertyList() {
		if (this.propertyList.isEmpty()) {
			ReadPropertyFile();
		}
		return this.propertyList;
	}
}
