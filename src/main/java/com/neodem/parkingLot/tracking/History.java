package com.neodem.parkingLot.tracking;

import com.neodem.parkingLot.model.VehicleMove;

public interface History {

	/**
	 * return the last move 
	 * from history.
	 * @return
	 */
	VehicleMove getLastMove();
	
	/**
	 * return and remove the last move 
	 * from history.
	 * @return
	 */
	VehicleMove removeLastMove();
	
	/**
	 * add a move to the history
	 * @param move
	 */
	void addMove(VehicleMove move);
	
	/**
	 * reset the history
	 *
	 */
	void reset();
}
