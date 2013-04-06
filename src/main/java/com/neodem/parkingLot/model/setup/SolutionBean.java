package com.neodem.parkingLot.model.setup;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.vehicle.VehicleID;

/**
 * contains one move in a given solution.
 * 
 * @author Vince
 *
 */
public class SolutionBean implements Comparable {
	private int moveNumber;
	private VehicleID id;
	private Direction facing;
	private int distance;
	
	public SolutionBean(String number, String id, String facing, String distance) {
		moveNumber = Integer.parseInt(number);
		this.id = new VehicleID(id);
		this.facing = Direction.makeDirection(facing);
		this.distance = Integer.parseInt(distance);
	}
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public Direction getFacing() {
		return facing;
	}
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	public VehicleID getId() {
		return id;
	}
	public void setId(VehicleID id) {
		this.id = id;
	}
	public int getMoveNumber() {
		return moveNumber;
	}
	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}

	public int compareTo(Object o) {
		SolutionBean myClass = (SolutionBean) o;
		return new CompareToBuilder().append(this.moveNumber, myClass.moveNumber).toComparison();
	}
}
