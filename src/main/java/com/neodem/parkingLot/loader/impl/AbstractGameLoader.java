
package com.neodem.parkingLot.loader.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.neodem.parkingLot.file.Difficulty;
import com.neodem.parkingLot.loader.GameLoader;
import com.neodem.parkingLot.loader.GameLoaderException;
import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.board.Board;
import com.neodem.parkingLot.model.board.BoardException;
import com.neodem.parkingLot.model.setup.BoardBean;
import com.neodem.parkingLot.model.setup.GameSet;
import com.neodem.parkingLot.model.setup.SolutionBean;
import com.neodem.parkingLot.model.setup.VehicleSetupBean;
import com.neodem.parkingLot.vehicle.GraphicalCar;
import com.neodem.parkingLot.vehicle.GraphicalTruck;
import com.neodem.parkingLot.vehicle.KeyCar;
import com.neodem.parkingLot.vehicle.Vehicle;
import com.neodem.parkingLot.vehicle.VehicleID;

public abstract class AbstractGameLoader implements GameLoader {

	protected GameSet gameSet;

	public Map<VehicleID, Vehicle> getGame(GameContext game, String id) throws GameLoaderException {
		if (game == null) {
			throw new GameLoaderException("game context is null");
		}

		if (gameSet == null) {
			throw new GameLoaderException("gameSet not loaded");
		}

		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("id may not be blank");
		}

		BoardBean bean = gameSet.getBoard(new Integer(id));
		Map<VehicleID, Vehicle> vehicleMap = fillBoard(game, bean);
		return vehicleMap;
	}

	public Map<VehicleID, Vehicle> getRandomEasyGame(GameContext game) throws GameLoaderException {
		if (gameSet == null) {
			throw new GameLoaderException("gameSet not loaded");
		}

		BoardBean bean = gameSet.getRandomBoard(Difficulty.EASY);
		Map<VehicleID, Vehicle> vehicleMap = fillBoard(game, bean);
		return vehicleMap;

	}

	@SuppressWarnings("unchecked")
	public List<SolutionBean> getSolution(String id) throws GameLoaderException {
		if (gameSet == null) {
			throw new GameLoaderException("gameSet not loaded");
		}
		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("id may not be blank");
		}

		BoardBean bean = gameSet.getBoard(new Integer(id));
		List<SolutionBean> solution = bean.getSolutions();

		if (solution == null) {
			throw new GameLoaderException("solution not found");
		}
		Collections.sort(solution);
		return solution;
	}

	/**
	 * fill the board with the given bean and return a set of vehicles added.
	 */
	protected Map<VehicleID, Vehicle> fillBoard(GameContext game, BoardBean bean) throws GameLoaderException {
		if (game == null) {
			throw new GameLoaderException("gameContext not valid (null)");
		}

		if (bean == null) {
			throw new GameLoaderException("board bean not valid (null)");
		}

		Set<VehicleSetupBean> setups = bean.getSetups();
		Board board = game.getBoard();
		Map<VehicleID, Vehicle> vehicleMap = new HashMap<VehicleID, Vehicle>();
		// fill the board
		for (VehicleSetupBean element : setups) {
			try {
				Vehicle vehicle = makeVehicle(element, game);
				if (vehicle != null) {
					board.addVehicle(vehicle, vehicle.getFrontSpace());
					vehicleMap.put(vehicle.getId(), vehicle);
				}
			} catch (BoardException e) {
				String msg = "problem adding vehicle to board : " + e.getMessage();
				throw new GameLoaderException(msg);
			}
		}

		return vehicleMap;
	}

	/**
	 * children need to load the data in some way
	 * 
	 */
	protected abstract boolean loadGameSet();

	public AbstractGameLoader() {
	}

	protected void init() {	
	}
	
	public boolean load() {
		init();
		return loadGameSet();
	}

	protected Vehicle makeVehicle(VehicleSetupBean element, GameContext game) {
		if (element == null) {
			throw new IllegalArgumentException("element may not be null");
		}

		Vehicle vehicle = null;
		if (element.getType().equalsIgnoreCase("CAR")) {
			vehicle = new GraphicalCar(element.getId(), element.getFacing(), element.getFrontSpace(), game);
		}
		if (element.getType().equalsIgnoreCase("TRUCK")) {
			vehicle = new GraphicalTruck(element.getId(), element.getFacing(), element.getFrontSpace(), game);
		}
		if (element.getType().equalsIgnoreCase("KEY")) {
			vehicle = new KeyCar(element.getId(), element.getFacing(), element.getFrontSpace(), game);
		}

		return vehicle;
	}
}
