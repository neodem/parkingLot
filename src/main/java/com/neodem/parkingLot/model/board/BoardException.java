package com.neodem.parkingLot.model.board;

@SuppressWarnings("serial")
public class BoardException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BoardException() {
	}

	public BoardException(String arg0) {
		super(arg0);
	}

	public BoardException(Throwable arg0) {
		super(arg0);
	}

	public BoardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
