
import java.io.File;

import datamanagement.*;
import processor.*;
import ui.*;
import logging.*;

public class Main {
	public static void main(String[] args) {
		//check if the number of inputs is 5 inputs long, otherwise return an error
		if (args.length != 5) {
			System.out.println("The number of inputs are incorrect! Please make sure you have 5 inputs");
			System.exit(0);
		}
		//check if the input type is correct (json or csv)
		InputFileType violationFileType = CheckType(args[0]);
		if (violationFileType == null) {
			System.out.println("The format of parking violation file is incorrect! Please input either 'json' or 'csv'!");
			System.exit(0);
		}
		
		//get input files
		String violationFileName = args[1];
		String propertyFileName = args[2];
		String populationFileName = args[3];
		//check if input files exist and can be opened
		if (!CheckInputFile(violationFileName) || !CheckInputFile(propertyFileName) || !CheckInputFile(populationFileName)) {
			System.exit(0);
		}
		
		//instantiate logger with log file
		String logFileName = args[4];
		Logger logger = Logger.getInstance(logFileName);
		if (logger.isFileLocked()) { 
			System.exit(0);			
		}
		logger.log(args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]); //log all inputs
		
		//initialize reading of property file
		PropertyProcessor propertyProcessor = new PropertyProcessor(propertyFileName);
		propertyProcessor.run(); //read the file on a separate thread...Start this first since it's the most time consuming read with the most data
		
		//initialize reading of population file
		PopulationReader populationReader = new PopulationReader(populationFileName);
		populationReader.run(); //read the file on a separate thread
		
		
		//initialize reading of parking violation file
		ViolationProcessor violationProcess = null;
		if (violationFileType == InputFileType.CSV) {
			violationProcess = new ViolationCSVProcessor(violationFileName);
		}
		else { //otherwise it's a JSON file. We previously checked that the input file is either a JSON or CSV
			violationProcess = new ViolationJSONProcessor(violationFileName);
		}
		violationProcess.run(); //read the file on a separate thread
	
		//Join threads at end of read
		try {
			propertyProcessor.join();
			violationProcess.join();
			populationReader.join();
		} catch (InterruptedException e) {
			System.out.println("Threads used to read files were interrupted.\n");
			System.exit(0);
		}
		
		UserInput input = new UserInput(violationProcess, populationReader, propertyProcessor);
		input.RunApplication();
		
	}
	
	/**
	 * Check the type of input file
	 * @param type
	 * @return
	 */
	private static InputFileType CheckType(String type) {
		String lowType = type.toLowerCase();
		if ("csv".equals(lowType)) { //if input is text then assign type
			return InputFileType.CSV;
		}
		else if ("json".equals(lowType)) { //if input is json then assign type
			return InputFileType.JSON;
		}
		return null; //otherwise return null
	}
	
	/**
	 * Check if the input file exists and can be read. If not then return false to end the program
	 * @param file
	 * @return
	 */
	public static boolean CheckInputFile(String fileName) {
		File file = new File(fileName); //create file object
		if (!file.exists()) { //if the file does not exist then return an error
			System.out.println(fileName + " does not exist!");
			return false;
		}
		if (!file.canRead()) { //if the file cannot be read then return an error
			System.out.println(fileName + " cannot be read! \nThe file provided could not be accessed due to administrator security permissions/file locks." + 
					"Please verify that the filepath is valid and the file is not locked!");
			return false;
		}
		return true;
	}
}
