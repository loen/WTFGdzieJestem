package com.WTFGdzieJestem;

public class LocationObject {

	private double longitude;
	private double lattitude;
	private String locationName;
	
	public LocationObject(double longitude, double lattitude, String locationName){
		this.longitude = longitude;
		this.lattitude = lattitude;
		this.locationName = locationName;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	@Override
	public String toString() {
		return "name:" + locationName + " longitude:" + longitude +
		" lattitude:" + lattitude;
	}
	
}
