package com.neodem.parkingLot.model;

import org.apache.commons.lang.StringUtils;

public enum Direction {
	NORTH, SOUTH, EAST, WEST;

	public Direction oppositeDirection() {
		if(this.equals(NORTH)) return SOUTH;
		if(this.equals(SOUTH)) return NORTH;
		if(this.equals(EAST)) return WEST;
		if(this.equals(WEST)) return EAST;
		return null;
	}
	
	private Direction() {
	}
	
	public static Direction makeDirection(String direction) {
		if(StringUtils.isBlank(direction)) { 
			return null;
		}
		
		if(direction.equalsIgnoreCase("NORTH") || direction.equalsIgnoreCase("UP")) {
			return NORTH;
		}
		
		if(direction.equalsIgnoreCase("SOUTH") || direction.equalsIgnoreCase("DOWN")) {
			return SOUTH;
		}
		
		if(direction.equalsIgnoreCase("EAST") || direction.equalsIgnoreCase("RIGHT")) {
			return EAST;
		}
		
		if(direction.equalsIgnoreCase("WEST") || direction.equalsIgnoreCase("LEFT")) {
			return WEST;
		}
		
		return null;
	}
}
