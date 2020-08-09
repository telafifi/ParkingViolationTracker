package edu.upenn.cit594.data;

public class Property implements Comparable {

	private String zipCode; //zip code of property
	private Double marketValue; //market value of property
	private Double totalLivableArea; //total livable area of that property
	
	public Property(String zipCode, double marketValue, double totalLivableArea) {
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
	}
	
	//Getters and Setters
	public String getZipCode() {
		return zipCode;
	}
	

	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}
	
	//override the equals method of the object to check if the properties have the same zipcode marketvalue and total livable area
	@Override
	public boolean equals(Object otherProperty) {
		Property myOtherProperty = (Property)otherProperty;
		return zipCode.equals(myOtherProperty.getZipCode()) && marketValue.equals(myOtherProperty.getMarketValue()) && totalLivableArea.equals(myOtherProperty.getTotalLivableArea());
	}

	//comparison based on comparing zip codes, then market  value then livable area
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
