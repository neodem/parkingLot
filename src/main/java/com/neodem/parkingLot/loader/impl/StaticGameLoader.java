
package com.neodem.parkingLot.loader.impl;

import com.neodem.parkingLot.file.Difficulty;
import com.neodem.parkingLot.model.setup.BoardBean;
import com.neodem.parkingLot.model.setup.GameSet;
import com.neodem.parkingLot.model.setup.SolutionBean;
import com.neodem.parkingLot.model.setup.VehicleSetupBean;

public class StaticGameLoader extends AbstractGameLoader {

	protected BoardBean board = null;

	public StaticGameLoader() {
	}
	
	@Override
	protected void init() {
		super.init();
		gameSet = new GameSet();
		board = new BoardBean(1, Difficulty.EASY);
		loadBoardBean();
		gameSet.addBoardBean(board);
	}

	@Override
	protected boolean loadGameSet() {
		return true;
	}

	private void loadBoardBean() {
		//addKey("X", "east", 14);
		addCar("A", "east", 1);
		//addCar("B", "north", 24);
		//addCar("C", "west", 28);
		 addTruck("O", "north", 5);
		//addTruck("P", "south", 18);
		// addTruck("Q", "south", 21);
		//addTruck("R", "east", 34);
		addSoultion(1, "C", "west", 3);
		addSoultion(2, "O", "south", 3);
		addSoultion(3, "A", "east", 1);
		addSoultion(4, "P", "north", 1);
		addSoultion(5, "B", "north", 1);
		addSoultion(6, "R", "west", 2);
		addSoultion(7, "Q", "south", 2);
		addSoultion(8, "X", "east", 3);
	}

	protected void addSoultion(int move, String id, String dir, int distance) {
		SolutionBean bean = new SolutionBean(Integer.toString(move), id, dir, Integer.toString(distance));
		board.addSoution(bean);
	}

	protected void addCar(String id, String dir, int start) {
		VehicleSetupBean bean = new VehicleSetupBean("CAR", id, dir, Integer.toString(start));
		board.addVehicle(bean);
	}

	protected void addTruck(String id, String dir, int start) {
		VehicleSetupBean bean = new VehicleSetupBean("TRUCK", id, dir, Integer.toString(start));
		board.addVehicle(bean);
	}

	protected void addKey(String id, String dir, int start) {
		VehicleSetupBean bean = new VehicleSetupBean("KEY", id, dir, Integer.toString(start));
		board.addVehicle(bean);
	}
}
