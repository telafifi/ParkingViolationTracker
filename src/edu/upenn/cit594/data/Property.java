package edu.upenn.cit594.data;

public class Property {
	private String zipCode;
	private double marketValue;
	private double totalLivableArea;
	
	public Property(String zipCode, double marketValue, double totalLivableArea) {
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
	}
	
	//Getters and Setters
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}

	public void setTotalLivableArea(double totalLivableArea) {
		this.totalLivableArea = totalLivableArea;
	}

	
}
