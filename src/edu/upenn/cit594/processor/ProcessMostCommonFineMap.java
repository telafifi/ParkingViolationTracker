package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

public class ProcessMostCommonFineMap {
	private ViolationProcessor violationProcess;
	private PopulationReader populationReader;
	private PropertyProcessor propProcessor;
	private PropertiesReader propertyReader;
	private TreeSet<FinesInZip> finesInZips;
	
	/**
	 * Constructor
	 * @param violationProcess
	 * @param populationReader
	 * @param propertyReader
	 * @param propProcessor
	 */
	public ProcessMostCommonFineMap(ViolationProcessor violationProcess, PopulationReader populationReader, PropertiesReader propertyReader, PropertyProcessor propProcessor) {
		this.violationProcess = violationProcess;
		this.populationReader = populationReader;
		this.propertyReader = propertyReader;
		this.propProcessor = propProcessor;
		finesInZips = new TreeSet<FinesInZip>(); //tree set to keep values sorted
	}
	
	/**
	 * Go through the fine list and get the most common fine for each zip code
	 * @param fineDescriptionSet
	 */
	private void MostCommonFinesCollector() {
		List<ParkingViolation> parkingViolations = violationProcess.getParkingViolations(); //get list of parking violations
		HashMap<String, Integer> populationMap = populationReader.getPopulationMap(); //get population map
		for (String zipCode: populationMap.keySet()) { //go through zip codes in population map
			int livableAreaPerCapita = propProcessor.getTotalValuePerCapita(zipCode, populationReader.getPopulationMap(), new LivableAreaCollector()); //calculate the total value per capita
			FinesInZip zipFine = new FinesInZip(zipCode, livableAreaPerCapita);
			for (ParkingViolation fine : parkingViolations) {  //go through list of violations
				if (fine.getZip_code().equals(zipCode)) { //check if zipcodes are equal
					zipFine.UpdateFineMap(fine.getDescription()); //update the fine map
				}
			}
			finesInZips.add(zipFine); //add to tree set
		}
		
	}
	
	/**
	 * Get the most common fines map per zip code sorted from lowest to highest livable area
	 * @param fineDescriptionSet
	 * @return
	 */
	public String GetCommonFinesMap() {
		MostCommonFinesCollector(); //run processor to build the treeSet of fines
		StringBuilder buildMapOutput = new StringBuilder(); //build a string builder
		buildMapOutput.append("Zip   | Most Common Fine\n"); //header
		for (FinesInZip fineDescription : finesInZips) { //go through list of fines in Set
			buildMapOutput.append(fineDescription.getZipCode() + " | " + fineDescription.getMostCommonFine() + "\n");
		}
		String commonFineOutput = buildMapOutput.toString(); //store the values into the output string
		return commonFineOutput;
	}
}
