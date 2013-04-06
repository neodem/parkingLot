package com.neodem.parkingLot.main;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.event.GameEventListener;
import com.neodem.parkingLot.loader.GameLoader;
import com.neodem.parkingLot.loader.GameLoaderException;
import com.neodem.parkingLot.loader.impl.XMLFileGameLoader;
import com.neodem.parkingLot.model.board.Board;
import com.neodem.parkingLot.model.space.Space;
import com.neodem.parkingLot.model.space.SpaceModel;
import com.neodem.parkingLot.tracking.History;
import com.neodem.parkingLot.tracking.MemoryHistory;
import com.neodem.parkingLot.vehicle.Vehicle;
import com.neodem.parkingLot.vehicle.VehicleID;

/**
 * this is the holder of the actual model info for
 * the game
 * 
 * @author Vince
 *
 */
public class GameContext {
	private static final Space WINNINGSPACE = new Space(17);
		
	private static final int BOARD_SIZE = 6;

	private Board board;

	private GameLoader loader;

	Map<VehicleID, Vehicle> vehicles;

	protected SpaceModel spaceModel;
	protected History history;
	
	public GameContext() {

	}
	
	/**
	 * called by the game to tell us that we are to quit
	 *
	 */
	public void quit() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * initialize and setup the game
	 * 
	 */
	public void init() {
		URL url = ClassLoader.getSystemResource("board1.xml");
		File file = new File(url.getPath());
		loader = new XMLFileGameLoader(file);
		
	//	loader = new StaticGameLoader();
		loader.load();
	
		spaceModel = new SpaceModel(BOARD_SIZE);
		board = new Board(this, spaceModel);

		history = new MemoryHistory(this);
		
		try {
			vehicles = loader.getRandomEasyGame(this);
		} catch (GameLoaderException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Map<VehicleID, Vehicle> getVehicles() {
		return vehicles;
	}

	public int getBoardSize() {
		return BOARD_SIZE;
	}

	public GameLoader getLoader() {
		return loader;
	}

	public void setLoader(GameLoader loader) {
		this.loader = loader;
	}

	public Board getBoard() {
		return board;
	}

	public SpaceModel getSpaceModel() {
		return spaceModel;
	}

	public static Space getWinningSpace() {
		return WINNINGSPACE;
	}

	private Set<GameEventListener> listeners;

	public void sendEvent(GameEvent event) {
		sendEvent(event, null);
	}
	
	
	public void sendEvent(GameEvent event, Object payload) {
		if(payload != null) {
			event.payload = payload;
		}
		for (GameEventListener listener : listeners) {
			listener.handleEvent(event);
		}
	}

	public void addListener(GameEventListener l) {
		if(listeners == null) {
			listeners = new HashSet<GameEventListener>();
		}
		listeners.add(l);
	}

	public History getHistory() {
		return history;
	}
}
