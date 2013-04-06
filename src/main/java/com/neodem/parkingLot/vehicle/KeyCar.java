
package com.neodem.parkingLot.vehicle;

import java.awt.Rectangle;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.VehicleMove;
import com.neodem.parkingLot.model.board.VehicleNotOnBoardException;
import com.neodem.parkingLot.model.space.Space;

public class KeyCar extends GraphicalCar {

	public KeyCar(VehicleID id, Direction orientation, Space startSpace, GameContext game) {
		super(id, orientation, startSpace, game);
	}

	/**
	 * called when a person is ready to start moving the vehicle
	 * 
	 * @throws VehicleNotOnBoardException
	 * @return the clipping rectangle
	 */
	public Rectangle startMove() throws VehicleNotOnBoardException {
		this.moveable = true;
		this.distanceMoved = 0;

		// determine max in each direction the player can move
		// this will vary depending on walls and other vehicles

		int maxForward = this.game.getBoard().maxForward(this);
		int maxBackward = this.game.getBoard().maxBackward(this);

		this.maxForwardDistance = SPACE_WIDTH * maxForward;
		this.maxBackwardDistance = SPACE_WIDTH * maxBackward;

		// the key car can go farther (to get out)
		// TODO fix this so it only happens on the last square
		// this.maxForwardDistance += 25;

		// compute bounding rect (untested)
		int x, y;
		int w, h;
		x = locx - maxBackwardDistance;
		y = locy;
		w = maxForwardDistance + maxBackwardDistance + (SPACE_WIDTH * size);
		h = SPACE_WIDTH;

		return new Rectangle(x, y, w, h);
	}

	/**
	 * called after a person is done moving the object
	 * 
	 */
	public void endMove() {
		this.moveable = false;

		// determine direction moved and set distanceMoved to a pos value
		Direction movedDir = null;

		if (this.distanceMoved > 0) {
			movedDir = Direction.EAST;
		} else {
			movedDir = Direction.WEST;
			this.distanceMoved = -this.distanceMoved;
		}

		// adjust the vechicle to be on a square (and not in the middle)
		int spacesMoved = this.distanceMoved / SPACE_WIDTH;
		int remainder = this.distanceMoved % SPACE_WIDTH;

		if (remainder > (SPACE_WIDTH / 2)) {
			spacesMoved = spacesMoved + 1;
		}

		if (spacesMoved > 0) {
			// get the new frontSpace
			Space newFrontSpace = this.game.getSpaceModel().getSpace(this.frontSpace, movedDir, spacesMoved);

			// set it and update it
			setFrontSpace(newFrontSpace);
			if (!newFrontSpace.equals(GameContext.getWinningSpace())) {
				// if we didn't win we force the car into it's block
				updateGraphicLocation();
			}

			// alert the board of the move
			game.sendEvent(GameEvent.GUIMOVE, new VehicleMove(this, movedDir, spacesMoved));
		}
	}
}
