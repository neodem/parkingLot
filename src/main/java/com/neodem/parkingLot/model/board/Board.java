package com.neodem.parkingLot.model.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.event.GameEventListener;
import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.VehicleMove;
import com.neodem.parkingLot.model.space.Space;
import com.neodem.parkingLot.model.space.SpaceModel;
import com.neodem.parkingLot.vehicle.Vehicle;

public class Board implements GameEventListener {

	protected SpaceModel spaceModel;

	protected boolean displayBoard = true;
	

	private Map<Space, Vehicle> spaceMap;
	
	private GameContext game;

	public Board(GameContext context, SpaceModel spaceModel) {
		this.spaceModel = spaceModel;
		this.game = context;
		game.addListener(this);
		spaceMap = new HashMap<Space, Vehicle>();
	}

	public void displayBoard() {
		StringBuffer b = new StringBuffer();

		for (Iterator iter = spaceModel.iterator(); iter.hasNext();) {
			Space space = (Space) iter.next();

			Vehicle vehicle = spaceMap.get(space);
			if (vehicle == null) {
				b.append('.');
			} else {
				b.append(vehicle.getId());
			}

			if (spaceModel.isLastInRow(space)) {
				b.append('\n');
			}
		}
		System.out.println(b.toString());
	}

	/**
	 * add a car object to the board. Will return true if the car was added
	 * successfully.
	 * 
	 * @param vehicle
	 * @param frontSpace
	 * @return
	 * @throws BoardException
	 */
	public boolean addVehicle(Vehicle vehicle, Space frontSpace) throws BoardException {
		if (vehicle == null)
			return false;

		Space[] covering = getVehicleWouldCoverSpaces(vehicle, frontSpace);

		if (spacesOpen(covering)) {
			// add the car to the spaces
			for (int i = 0; i < covering.length; i++) {
				spaceMap.put(covering[i], vehicle);
			}
			return true;
		}
		throw new BoardException();
	}

	/**
	 * remove the vehicle from the board
	 * 
	 * @param vehicle
	 */
	public void removeVehicle(Vehicle vehicle) {
		Collection keys = spaceMap.keySet();
		List<Object> remove = new ArrayList<Object>();
		for (Object key : keys) {
			Vehicle v = spaceMap.get(key);
			if (v != null) {
				if (v.equals(vehicle)) {
					remove.add(key);
				}
			}
		}

		for (Object key : remove) {
			spaceMap.remove(key);
		}
	}

	/**
	 * attempt to move the car one space in the indicated direction. (will
	 * return true if move ok and completed)
	 * 
	 * @param car
	 * @param direction
	 * @return
	 */
	public boolean moveVehicle(Vehicle vehicle, Direction direction) throws BoardException {
		return moveVehicle(vehicle, direction, 1, true);
	}
	
	/**
	 * attempt to move the vehicle (will
	 * return true if move ok and completed)
	 * 
	 * @param move
	 * @return
	 */
	public boolean moveVehicle(VehicleMove move) throws BoardException {
		if(move.getVehicle() == null) return false;
		if(move.getDistance() < 1) return false;;
		if(move.getMovedDir() == null) return false;;
		
		return moveVehicle(move.getVehicle(), move.getMovedDir(), move.getDistance(), true);	
	}

	/**
	 * attempt to move the car a number of spaces in the indicated direction.
	 * (will return true if move ok and completed)
	 * 
	 * @param car
	 * @param direction
	 * @param distance
	 * @param updateVehicle
	 *            set to false to not update the vehicle of the move (in the
	 *            case that the vehicle made the move)
	 * @return
	 * @throws VehicleNotOnBoardException
	 */
	protected boolean moveVehicle(Vehicle vehicle, Direction direction, int distance, boolean updateVehicle)
			throws VehicleNotOnBoardException {
		if ((vehicle == null) || (direction == null)) {
			return false;
		}

		// is the vehicle on the board?
		if (spaceMap.containsValue(vehicle)) {
			// 1) can the car move in that direction?
			if (vehicle.canDriveInThisDirection(direction)) {

				// 2a) get the edge space for the car (this is the space that is
				// the front or back of vehicle (depending on direction of
				// move))
				Space edgeSpace = getEdgeSpace(vehicle, direction);

				// 2) detemine spaces the car will cover in its travels
				Space[] pathSpaces = spaceModel.determinePath(edgeSpace, direction, distance);

				// 3) does the path include walls?
				if (pathContainsWalls(pathSpaces)) {
					return false;
				}

				// 4) are these spaces all open?
				if (spacesOpenExclude(pathSpaces, vehicle)) {

					// 5) move the car

					// 5a) clear the old location on the spaceList
					removeVehicle(vehicle);

					// 5b) update the spaceList
					// get new edge space
					Space newEdgeSpace = spaceModel.getSpace(edgeSpace, direction, distance);
					Space newFrontSpace = null;
					if (vehicle.getOrientation().equals(direction)) {
						newFrontSpace = newEdgeSpace;
					} else {
						newFrontSpace = spaceModel.getSpace(newEdgeSpace, direction.oppositeDirection(), vehicle
								.getSize() - 1);
					}

					try {
						addVehicle(vehicle, newFrontSpace);
					} catch (BoardException e) {
						// this should never happen
						e.printStackTrace();
					}

					if (updateVehicle) {
						vehicle.setFrontSpace(newFrontSpace);
					}

					// 6) store the move in the move history
					game.sendEvent(GameEvent.MOVE_OCCURED, new VehicleMove(vehicle, direction, distance));
					
					//history.addMove(vehicle, direction, distance);
					
					if(displayBoard) {
						displayBoard();
					}
					return true;
				}
			}
		} else {
			throw new VehicleNotOnBoardException();
		}
		return false;
	}

	protected boolean pathContainsWalls(Space[] pathSpaces) {
		for (int i = 0; i < pathSpaces.length; i++) {
			Space space = pathSpaces[i];
			if (space != null) {
				if (space.isWall()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * return the spaces that the vehicle would occupy if it were to have its
	 * front placed on the given space
	 * 
	 * @param vehicle
	 * @param frontSpace
	 * @return
	 */
	protected Space[] getVehicleWouldCoverSpaces(Vehicle vehicle, Space frontSpace) {
		int length = vehicle.getSize();
		Direction vehicleFacing = vehicle.getOrientation();
		Space[] covering = spaceModel.determinePath(frontSpace, vehicleFacing.oppositeDirection(), length - 1);
		return covering;
	}

	/**
	 * return a list of spaces the vehicle is occupying on the board
	 * 
	 * @param vehicle
	 * @return
	 */
	protected List<Space> getVehicleOccupying(Vehicle vehicle) {
		List<Space> spaceList = new ArrayList<Space>();
		if (spaceMap.containsValue(vehicle)) {
			for (Iterator iter = spaceModel.iterator(); iter.hasNext();) {
				Space space = (Space) iter.next();
				Vehicle test = spaceMap.get(space);
				if (vehicle.equals(test)) {
					spaceList.add(space);
				}
			}
		}
		return spaceList;
	}

	/**
	 * get the edge space of a vehicle. Return null if the vehicle isn't facing
	 * in an appropriate direction or if the vehicle is not on the board
	 * 
	 * @param vehicle
	 * @param direction
	 * @return
	 * @throws VehicleNotOnBoardException
	 */
	protected Space getEdgeSpace(Vehicle vehicle, Direction direction) throws VehicleNotOnBoardException {
		if ((vehicle == null) || (direction == null))
			return null;

		if (vehicle.canDriveInThisDirection(direction)) {
			if (spaceMap.containsValue(vehicle)) {
				List<Space> spaceList = getVehicleOccupying(vehicle);
				if (spaceList.size() == vehicle.getSize()) {
					return spaceModel.getDirectionMostSpaceFromList(spaceList, direction);
				}
			} else {
				throw new VehicleNotOnBoardException();
			}
		}
		return null;
	}

	/**
	 * return true if the spaces given are free and clear
	 * 
	 * @param spaces
	 * @return
	 */
	protected boolean spacesOpen(Space[] spaces) {
		return spacesOpenExclude(spaces, null);
	}

	/**
	 * return true if the space is open
	 * 
	 * @param spaceNumber
	 * @return
	 */
	protected boolean spaceOpen(Space space) {
		return spaceOpenExclude(space, null);
	}

	/**
	 * return true if the space is fee and clear of all vehicles and walls
	 * except the one given. If vehicle is null this will act just like the non
	 * vehicle version of spaceOpen()
	 * 
	 * @param spaceNumber
	 * @return
	 */
	protected boolean spaceOpenExclude(Space space, Vehicle vehicle) {
		if (space.isWall()) {
			return false;
		}
		Vehicle onSpace = spaceMap.get(space);
		if (onSpace != null) {
			if (!onSpace.equals(vehicle)) {
				// something is on the space and its not our vehicle
				return false;
			}
		}
		return true;
	}

	/**
	 * return true if the spaces given are fee and clear of all vehicles except
	 * the one given. If vehicle is null this will act just like the non vehicle
	 * version of spacesOpen()
	 * 
	 * @param pathSpaces
	 * @param vehicle
	 * @return
	 */
	protected boolean spacesOpenExclude(Space[] spaces, Vehicle vehicle) {
		// check to see if the spaces are open
		for (int i = 0; i < spaces.length; i++) {
			if (!spaceOpenExclude(spaces[i], vehicle)) {
				// something is on a space
				return false;
			}
		}
		return true;
	}

	/**
	 * determine the number of open spaces in a given direction.
	 * 
	 * @param direction
	 * @param startSpace
	 * @return
	 */
	protected int maxTravel(Vehicle vehicle, Direction direction, Space startSpace) {
		int max = 0;

		// at most the vehicle can travel the length of a row
		// minus the size of the vehicle
		int minTravel = spaceModel.getRowLength() - vehicle.getSize();
		for (int i = 1; i <= minTravel; i++) {
			Space testSpace = spaceModel.getSpace(startSpace, direction, i);
			if (!spaceOpen(testSpace)) {
				break;
			}
			max++;
		}

		return max;
	}

	/**
	 * return the front space of the vehicle
	 * 
	 * @param v
	 * @return
	 * @throws VehicleNotOnBoardException
	 */
	public Space getFrontLocation(Vehicle vehicle) throws VehicleNotOnBoardException {
		if (!spaceMap.containsValue(vehicle)) {
			throw new VehicleNotOnBoardException();
		}

		return getEdgeSpace(vehicle, vehicle.getOrientation());
	}

	public SpaceModel getSpaceModel() {
		return spaceModel;
	}

	/**
	 * determine the maximum number of spaces this vehicle can travel in its
	 * forward direction
	 * 
	 * @param vehicle
	 * @return
	 * @throws VehicleNotOnBoardException
	 */
	public int maxForward(Vehicle vehicle) throws VehicleNotOnBoardException {
		Direction forward = vehicle.getOrientation();
		Space frontSpace = getEdgeSpace(vehicle, forward);
		return maxTravel(vehicle, forward, frontSpace);
	}

	/**
	 * determine the maximum number of spaces this vehicle can travel in its
	 * backward direction
	 * 
	 * @param vehicle
	 * @return
	 * @throws VehicleNotOnBoardException
	 */
	public int maxBackward(Vehicle vehicle) throws VehicleNotOnBoardException {
		Direction backward = vehicle.getOrientation().oppositeDirection();
		Space backSpace = getEdgeSpace(vehicle, backward);
		return maxTravel(vehicle, backward, backSpace);
	}

	public void handleEvent(GameEvent event) {
		if(GameEvent.GUIMOVE.equals(event)) {
			try {
				VehicleMove move = (VehicleMove)event.payload;
				moveVehicle(move);
			} catch (BoardException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
