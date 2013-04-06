package com.neodem.parkingLot.loader;

import java.util.List;
import java.util.Map;

import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.setup.SolutionBean;
import com.neodem.parkingLot.vehicle.Vehicle;
import com.neodem.parkingLot.vehicle.VehicleID;

public interface GameLoader {
	
	/**
	 * load the games and solutions into memory
	 * @return
	 */
	public boolean load();
	
	/**
	 * load a particular game into a gamecontext 
	 * @param game
	 * @param id the id of the game to load
	 * @return
	 * @throws GameLoaderException
	 */
	public Map<VehicleID, Vehicle> getGame(GameContext game, String id) throws GameLoaderException;
	
	/**
	 * 
	 * @param board
	 * @return
	 * @throws GameLoaderException
	 */
	public Map<VehicleID, Vehicle> getRandomEasyGame(GameContext game) throws GameLoaderException;
	
	
	/**
	 * assumes that game has been loaded with a call to loadGame()
	 * else it will throw an exception;
	 * @return
	 * @throws GameLoaderException if a game hasn't been loaded already
	 */
	public List<SolutionBean> getSolution(String id) throws GameLoaderException;
}
