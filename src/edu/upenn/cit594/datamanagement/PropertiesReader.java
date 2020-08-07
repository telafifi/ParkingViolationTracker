package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.*;
import edu.upenn.cit594.data.*;

public class PropertiesReader {
	private String fileName;
	private int zipCodeColumn;
	private int marketValueColumn;
	private int livableAreaColumn;
	private int totalNumFields;
	private List<Property> propertyList; //holds the property and the number of times that property has identicle objects in the provided file
	BufferedReader fileReader;
	
	public PropertiesReader(String fileName) {
		this.fileName = fileName;
		propertyList = new ArrayList<Property>();
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
	
	public void Print() {
		for (Property prop : propertyList) {
			System.out.println(prop.getZipCode() + "\t" + prop.getMarketValue() + "\t" + prop.getTotalLivableArea());
		}
	}
	
	/**
	 * Read the property file and store the list of inputs in the propertyList
	 */
	private void ReadPropertyFile() {
		File file = new File(this.fileName);
		try {
			fileReader = new BufferedReader(new FileReader(file)); //buffered reader to read file
			String line = fileReader.readLine();
			if (line == null) {
				fileReader.close();
				return;
			}
			GetRequiredColumnLocations(line.split(",")); //get the locations of the correct headers
			int i = 1;
			while ((line = fileReader.readLine()) != null) {
				String[] lineComponents = line.split(","); //try splitting the line with commas
				if (lineComponents.length != this.totalNumFields) { //if the total number of components of that line does not match the total number of header fields
					lineComponents = ParseCSVFile(line); //parse the line using regex to not split lines at commas between quotations
				}
				String zipCode = AdjustZipCode(GetLineComponent(this.zipCodeColumn, lineComponents));
				Double marketValue = GetNumericComponent(GetLineComponent(this.marketValueColumn, lineComponents));
				Double totalLivableArea = GetNumericComponent(GetLineComponent(this.livableAreaColumn, lineComponents));
				Property currentProp = new Property(zipCode, marketValue, totalLivableArea, 1);
				propertyList.add(currentProp);
				i++;
			}
		}
		catch (Exception e) {
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
		String otherThanQuote = " [^\"] ";
        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        String regex = String.format("(?x) "+ // enable comments, ignore white spaces
                ",                         "+ // match a comma
                "(?=                       "+ // start positive look ahead
                "  (?:                     "+ //   start non-capturing group 1
                "    %s*                   "+ //     match 'otherThanQuote' zero or more times
                "    %s                    "+ //     match 'quotedString'
                "  )*                      "+ //   end group 1 and repeat it zero or more times
                "  %s*                     "+ //   match 'otherThanQuote'
                "  $                       "+ // match the end of the string
                ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);
        return line.split(regex, -1);
	}
	
	/**
	 * Search the header row for locations of zip_code, market_value, and total_livable_area column locations
	 * @param headerComponents
	 */
	private void GetRequiredColumnLocations(String[] headerComponents) {
		this.totalNumFields = headerComponents.length;
		for (int i = 0; i < totalNumFields; i++) {
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
	
	private String GetLineComponent(int column, String[] lineComponents) {
		if (column < lineComponents.length) { //this is a check if the column is empty and at the end of the line, this would cause the split to be short
			return lineComponents[column];
		}
		return "";
	}
	
	/**
	 * if the length of the zipcode is larger than 5 numbers then only grab that value. 
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
		try {
			double returnVal = Double.parseDouble(lineComponent); //parse the string
			return returnVal;
		}
		catch (Exception e) {
			return Double.NaN; //if the value could not be parsed then return a Not a number value
		}
	}
}
