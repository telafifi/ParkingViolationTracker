package edu.upenn.cit594.logging;

import java.io.*;

public class Logger {
private FileWriter out; //private field to write to file
	
	/**
	 * Private constructor to log tweets
	 * @param filename
	 */
	private Logger (String fileName) {
		try {
			File file = new File(fileName);
			if (!file.canWrite()) {
				instance = null;
			}
			else {
				out = new FileWriter(file, true);
			}
		}
		catch (Exception ex) {
			instance = null;
		}
	}
	
	//Singleton instance
	private static Logger instance;
	
	/**
	 * logger accessor method
	 * @return
	 */
	public static Logger getInstance() {
		return instance;
	}
	
	/**
	 * logger accessor method with filename input
	 * @param fileName
	 * @return
	 */
	public static Logger getInstance(String fileName) {
		if (instance == null) {
			instance = new Logger(fileName);
		}
		return instance;
	}
	
	/**
	 * logger method to log a new instance to the file
	 * @param message
	 */
	public synchronized void log (String message) { //synchronized so that two threads dont log at the same time and cause overlap errors
		long currentTime = System.currentTimeMillis(); //get the current time
		try {
			out.write(currentTime + " " + message + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("Could not write to log file!");
		}
	}
	
	/**
	 * Check if the file could not be written due to permission issues or locks
	 */
	public boolean isFileLocked() {
		if (out == null) {
			System.out.println("The log file provided could not be accessed due to administrator security permissions/file locks." +  
					"Please verify that the filepath is valid and the file is not locked!");
			return true;
		}
		return false;
	}
}
