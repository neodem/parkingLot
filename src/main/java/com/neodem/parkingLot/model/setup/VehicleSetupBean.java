package com.neodem.parkingLot.model.setup;

import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.space.Space;
import com.neodem.parkingLot.vehicle.VehicleID;

public class VehicleSetupBean {
	private String type;
	private VehicleID id;
	private Direction facing;
	private Space frontSpace;
		
	public VehicleSetupBean() {
		// TODO Auto-generated constructor stub
	}
	
	public VehicleSetupBean(String type, String id, String facing, String start) {
		this.type = type;
		this.id = new VehicleID(id);
		this.facing = Direction.makeDirection(facing);
		this.frontSpace = new Space(Integer.parseInt(start));
	}	
	
	public Direction getFacing() {
		return facing;
	}
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	public Space getFrontSpace() {
		return frontSpace;
	}
	public void setFrontSpace(Space frontSpace) {
		this.frontSpace = frontSpace;
	}
	public VehicleID getId() {
		return id;
	}
	public void setId(VehicleID id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
