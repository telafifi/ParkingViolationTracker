package edu.upenn.cit594.datamanagement;

import java.io.*;
import java.util.regex.Pattern;

public class InputFileChecks {
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
	
	/**
	 * Check if the string can be converted to an integer
	 * @param lineComponents
	 * @return
	 */
	public static boolean isStringInteger(String lineComponents) {
		return Pattern.matches("[0-9]*", lineComponents); //regex to check if the value is an integer
	}
}
