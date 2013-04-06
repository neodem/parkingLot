package com.neodem.parkingLot.event;


public enum GameEvent {
	
	/**
	 * will be sent out by the BoardPanel
	 * when it sees that the game 
	 * has been won.
	 */
	GAME_WON, 
	
	/**
	 * will get sent when a move has
	 * occured (payload == move)
	 */
	MOVE_OCCURED,
	
	/**
	 * will be sent when a move has been recorded
	 * and available for undo (payload == move)
	 */
	MOVE_RECORDED, 
	
	/**
	 * sent when the GUI/player has requested a move 
	 */
	GUIMOVE, 
	
	/** 
	 * when the board should be redrawn
	 */
	REDRAW_BOARD, 
	
	/**
	 * when a move was removed from history
	 */
	MOVE_REMOVED;

	public Object payload;
	
}
