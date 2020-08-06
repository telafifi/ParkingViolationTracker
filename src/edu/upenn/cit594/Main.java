package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;

import java.util.Map;

import edu.upenn.cit594.data.*;

public class Main {
	public static void main(String[] args) {
//		ViolationProcessor myProcess = new ViolationCSVProcessor("parking.csv");
//		myProcess.processViolations();
		System.out.println("start");
		PropertiesReader myReader = new PropertiesReader("properties.csv");
		myReader.getPropertyList();
		myReader.Print();
		System.out.println("done");
	}
}
