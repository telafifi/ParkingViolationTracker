package datamanagement;

import java.io.*;
import java.util.regex.Pattern;

public class InputFileChecks {
	
	
	/**
	 * Check if the string can be converted to an integer
	 * @param lineComponents
	 * @return
	 */
	public static boolean isStringInteger(String lineComponents) { //stored as a static variable to be used throughout the different readers
		return Pattern.matches("[0-9]*", lineComponents); //regex to check if the value is an integer
	}
}
