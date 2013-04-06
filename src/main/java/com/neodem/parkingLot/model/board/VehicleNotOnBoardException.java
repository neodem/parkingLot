package com.neodem.parkingLot.model.board;

public class VehicleNotOnBoardException extends BoardException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VehicleNotOnBoardException() {
	}

	public VehicleNotOnBoardException(String message) {
		super(message);
	}

	public VehicleNotOnBoardException(Throwable cause) {
		super(cause);
	}

	public VehicleNotOnBoardException(String message, Throwable cause) {
		super(message, cause);
	}

}
