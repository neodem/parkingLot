package com.neodem.parkingLot.vehicle;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.space.Space;

public abstract class Vehicle  {
	
	/**
	 * the direction the car faces
	 */
	protected Direction orientation;

	protected int size;

	protected VehicleID id;

	protected Space frontSpace;

	public String toString() {
		return "vehicle " + id;
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public Vehicle(VehicleID id, Direction orientation, Space startSpace, int size) {
		if (orientation == null) {
			throw new IllegalArgumentException("orientation may not be null");
		}

		this.orientation = orientation;
		this.id = id;
		this.size = size;
		setFrontSpace(startSpace);
	}

	public Direction getOrientation() {
		return orientation;
	}

	public void setOrientation(Direction direction) {
		this.orientation = direction;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * return true if the car can move in this direction
	 * 
	 * @param direction
	 * @return
	 */
	public boolean canDriveInThisDirection(Direction direction) {
		if (direction.equals(orientation))
			return true;
		if (direction.equals(orientation.oppositeDirection()))
			return true;
		return false;
	}

	public VehicleID getId() {
		return id;
	}

	public void setId(VehicleID id) {
		this.id = id;
	}


	public Space getFrontSpace() {
		return frontSpace;
	}

	public void setFrontSpace(Space frontSpace) {
		this.frontSpace = frontSpace;
	}
}
