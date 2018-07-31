package com.hlibrary.action.entity;

/**
 * 
 * @author 林文辉
 * @since 2015-01-27
 * @since v 1.0.0
 * 
 */
public class LocationVo {

	private double lat;
	private double lng;
	private String addr;
	private String city;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
