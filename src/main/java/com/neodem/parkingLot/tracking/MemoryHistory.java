package com.neodem.parkingLot.tracking;

import java.util.ArrayList;
import java.util.List;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.event.GameEventListener;
import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.VehicleMove;

public class MemoryHistory implements History, GameEventListener {

	private int currentMove;
	private List<VehicleMove> moves;
	private GameContext game;

	public MemoryHistory(GameContext game) {
		this.game = game;
		game.addListener(this);
		reset();
	}

	public void reset() {
		this.currentMove = 0;
		moves = new ArrayList<VehicleMove>();
	}

	public void addMove(VehicleMove move) {
		if(move.getVehicle() == null) return;
		if(move.getMovedDir() == null) return;
		if(move.getDistance() < 1) return;
		
		moves.add(0, move);
		System.out.println("move(" + currentMove +")" + move);
		currentMove++;
		game.sendEvent(GameEvent.MOVE_RECORDED, move);
	}

	public VehicleMove removeLastMove() {
		VehicleMove move = moves.remove(0);
		System.out.println("remove(" + (currentMove-1) + ")" + move);
		currentMove--;
		game.sendEvent(GameEvent.MOVE_REMOVED, move);
		return move;
	}

	public void handleEvent(GameEvent event) {
		if(GameEvent.MOVE_OCCURED.equals(event)) {
			addMove((VehicleMove)event.payload);
		}
	}

	public VehicleMove getLastMove() {
		VehicleMove move = moves.get(0);
		return move;
	}

}
