package edu.upenn.cit594.data;

public class ParkingViolation {
	private String date; //date of the fine
	private int fine; //amount of fine
	private String description; //description of fine
	private String plate_id; //vehicle id
	private String state; //license plate state
	private int ticket_number; //fine ticket number
	private String zip_code; //zip code of incident
	
	public ParkingViolation(int ticket_number, String plate_id, String date, String zip_code, String description, int fine, String state) {
		this.ticket_number = ticket_number;
		this.plate_id = plate_id;
		this.date = date;
		this.zip_code = zip_code;
		this.description = description;
		this.fine = fine;
		this.state = state;
	}
	
	//Getters and Setters
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlate_id() {
		return plate_id;
	}
	public void setPlate_id(String plate_id) {
		this.plate_id = plate_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getTicket_number() {
		return ticket_number;
	}
	public void setTicket_number(int ticket_number) {
		this.ticket_number = ticket_number;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	
}
