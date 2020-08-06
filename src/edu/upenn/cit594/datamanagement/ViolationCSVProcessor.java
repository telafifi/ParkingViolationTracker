package edu.upenn.cit594.datamanagement;

public class ViolationCSVProcessor extends ViolationProcessor {
	public ViolationCSVProcessor(String fileName) {
		super(fileName);
	}

	@Override
	protected ViolationReader createReader(String fileName) {
		return new ViolationCSVReader(fileName);
	}
}
