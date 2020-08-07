package edu.upenn.cit594.data;

public class Property implements Comparable {

	private String zipCode;
	private Double marketValue;
	private Double totalLivableArea;
	private int numberOfSimilarProperties;
	
	public Property(String zipCode, double marketValue, double totalLivableArea, int numberOfSimilarProperties) {
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.numberOfSimilarProperties = numberOfSimilarProperties;
	}
	
	//Getters and Setters
	public String getZipCode() {
		return zipCode;
	}
	
	public int getNumberOfSimilarProperties() {
		return numberOfSimilarProperties;
	}

	public void setNumberOfSimilarProperties(int numberOfSimilarProperties) {
		this.numberOfSimilarProperties = numberOfSimilarProperties;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}
	
	@Override
	public boolean equals(Object otherProperty) {
		Property myOtherProperty = (Property)otherProperty;
		return zipCode.equals(myOtherProperty.getZipCode()) && marketValue.equals(myOtherProperty.getMarketValue()) && totalLivableArea.equals(myOtherProperty.getTotalLivableArea());
	}

	@Override
	public int compareTo(Object otherProperty) {
		Property myOtherProperty = (Property)otherProperty;
		int returnVal = zipCode.compareTo(myOtherProperty.getZipCode()); //compare zipcodes first
		if (returnVal == 0) {
			returnVal = marketValue.compareTo(myOtherProperty.getMarketValue()); //if zip codes are equal compare marketvalue
			if (returnVal == 0) {
				returnVal = totalLivableArea.compareTo(myOtherProperty.getTotalLivableArea()); //if marketvalues are the same compare totalLivableArea
			}
		}
		return returnVal;
	}

	
}
