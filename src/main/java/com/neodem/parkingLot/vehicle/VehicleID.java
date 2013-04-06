package com.neodem.parkingLot.vehicle;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class VehicleID {
	private String id;

	public VehicleID(String id) {
		super();
		this.id = id;
	}

	public VehicleID(char id) {
		super();
		this.id = "" + id;
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public String toString() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
