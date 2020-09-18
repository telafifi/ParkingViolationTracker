package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.*;

public class ViolationJSONProcessor extends ViolationProcessor {
	public ViolationJSONProcessor(String fileName) {
		super(fileName);
	}
	
	@Override
	protected ViolationReader createReader(String fileName) {
		return new ViolationJSONReader(fileName);
	}
}
