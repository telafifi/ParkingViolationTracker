package edu.upenn.cit594.data;

public class Property {

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

	
}
