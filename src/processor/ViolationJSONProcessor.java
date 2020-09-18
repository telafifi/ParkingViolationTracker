package processor;

import datamanagement.*;

public class ViolationJSONProcessor extends ViolationProcessor {
	public ViolationJSONProcessor(String fileName) {
		super(fileName);
	}
	
	@Override
	protected ViolationReader createReader(String fileName) {
		return new ViolationJSONReader(fileName);
	}
}
