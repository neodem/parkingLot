package com.neodem.parkingLot.model.space;

import org.apache.commons.lang.StringUtils;

public class Space {

	private int spaceNumber;

	protected boolean wall = false;

	public Space(int spaceNumber) {
		this.spaceNumber = spaceNumber;
	}

	public Space(String start) {
		if(StringUtils.isBlank(start)) {
			throw new IllegalArgumentException("start value string may not be blank");
		}
		if(!StringUtils.isNumeric(start)) {
			throw new IllegalArgumentException("start value string must be a number");
		}
		try {
			spaceNumber = Integer.parseInt(start);
		}
		catch (NumberFormatException n) {
			throw new IllegalArgumentException("start value string must contain an integer");
		}
	}

	protected int getAbsoluteSpace() {
		return spaceNumber;
	}

	public boolean isWall() {
		return wall;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Space) {
			Space other = (Space) o;
			return other.spaceNumber == this.spaceNumber;
		}

		return false;
	}

	public String toString() {
		return "" + spaceNumber;
	}

	@Override
	public int hashCode() {
		return spaceNumber;
	}
}
