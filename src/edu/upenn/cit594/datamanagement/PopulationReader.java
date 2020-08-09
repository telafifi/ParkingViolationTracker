package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Pattern;

import edu.upenn.cit594.logging.*;

public class PopulationReader extends Thread { //extend thread to read all files simultaneously at launch
	private String fileName;
	private HashMap<String, Integer> populationMap; //map to hold the zip code vs population
	private BufferedReader fileReader; //file reader
	private int totalPopulation; //total population in file (sum of all zip codes)
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public PopulationReader(String fileName) {
		this.fileName = fileName;
		this.populationMap = new HashMap<String, Integer>(); //instantiate map
		this.totalPopulation = 0; //set total population to zero
	}
	
	/**
	 * Return the population map. If the map is un-populated (empty) then read the population file prior to returning the map
	 * @return
	 */
	public HashMap<String, Integer> getPopulationMap() {
		if (this.populationMap.keySet().isEmpty()) {
			ReadPopulationFile();
		}
		return this.populationMap;
	}
	
	/**
	 * Read the population map
	 */
	private void ReadPopulationFile() {
		File file = new File(this.fileName); //instantiate file
		try {
			Logger logger = Logger.getInstance();
			logger.log(this.fileName); //log the time and filename that has been read
			fileReader = new BufferedReader(new FileReader(file)); //buffered reader to read file
			String line;
			while ((line = fileReader.readLine()) != null) { //go through the file
				String[] lineComponents = line.split(" "); //split line at space
				if (lineComponents.length == 2) { //only get the input line if the input has 2 values
					if (InputFileChecks.isStringInteger(lineComponents[1])) { //check if the population is a valid integer. If not then ignore the line
						String zip_code = lineComponents[0]; 
						int population = Integer.parseInt(lineComponents[1]);
						populationMap.put(zip_code, population); //add values to map
						totalPopulation = totalPopulation + population; //increase the total population while reading the file
					}
				}
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
	 * Used to read the file asynchronously while reading other files
	 */
	public void run() {
		this.ReadPopulationFile();
	}
	
	/**
	 * Get the total population sum of the entire run
	 * @return
	 */
	public int GetTotalPopulation() {
		return totalPopulation; //return the total population
	}
	
}
