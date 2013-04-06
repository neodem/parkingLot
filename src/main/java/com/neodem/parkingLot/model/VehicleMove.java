package com.neodem.parkingLot.model;

import com.neodem.parkingLot.vehicle.Vehicle;


public class VehicleMove {

	private Vehicle vehicle;
	private Direction movedDir;
	private int distance;
	
	public VehicleMove(Vehicle vehicle, Direction movedDir, int spacesMoved) {
		super();
		this.vehicle = vehicle;
		this.movedDir = movedDir;
		this.distance = spacesMoved;
	}

	public String toString() {
		StringBuffer b= new StringBuffer();
		b.append("m:");
		b.append(vehicle);
		b.append(":");
		b.append(distance);
		b.append(":");
		b.append(movedDir);
		return b.toString();
	}
	
	/**
	 * @return the movedDir
	 */
	public Direction getMovedDir() {
		return movedDir;
	}
	
	/**
	 * @param movedDir the movedDir to set
	 */
	public void setMovedDir(Direction movedDir) {
		this.movedDir = movedDir;
	}
	
	/**
	 * @return the spacesMoved
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * @param spacesMoved the spacesMoved to set
	 */
	public void setDistance(int spacesMoved) {
		this.distance = spacesMoved;
	}
	
	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


}
