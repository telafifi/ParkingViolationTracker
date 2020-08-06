package edu.upenn.cit594.logging;

import java.io.File;
import java.io.PrintWriter;

public class Logger {
private PrintWriter out; //private field to write to file
	
	/**
	 * Private constructor to log tweets
	 * @param filename
	 */
	private Logger (String fileName) {
		try {
			File file = new File(fileName);
			file.createNewFile();
			out = new PrintWriter(file);
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
	public void log (String message) {
		out.println(message);
		out.flush();
	}
	
	/**
	 * Check if the file could not be written due to permission issues or locks
	 */
	public boolean isFileLocked() {
		if (out == null) {
			return true;
		}
		return false;
	}
}
