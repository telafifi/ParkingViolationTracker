package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PopulationReader {
	String fileName;
	Map<String, Integer> populationMap;
	BufferedReader fileReader;
	
	public PopulationReader(String fileName) {
		this.fileName = fileName;
		this.populationMap = new HashMap<String, Integer>();
	}
	
	/**
	 * Return the population map. If the map is unpopulated (empty) then read the population file prior to returning the map
	 * @return
	 */
	public Map<String, Integer> getPopulationMap() {
		if (this.populationMap.keySet().isEmpty()) {
			ReadPopulationFile();
		}
		return this.populationMap;
	}
	
	/**
	 * Read the population map
	 */
	private void ReadPopulationFile() {
		File file = new File(this.fileName);
		try {
			fileReader = new BufferedReader(new FileReader(file)); //buffered reader to read file
			String line;
			while ((line = fileReader.readLine()) != null) {
				String[] lineComponents = line.split(" "); //split line at space
				if (lineComponents.length == 2) {
					String zip_code = lineComponents[0];
					int population = Integer.parseInt(lineComponents[1]);
					populationMap.put(zip_code, population);
				}
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
	
}
