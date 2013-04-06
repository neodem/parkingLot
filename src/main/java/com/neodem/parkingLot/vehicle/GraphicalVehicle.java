
package com.neodem.parkingLot.vehicle;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.GraphicalObject;
import com.neodem.parkingLot.model.VehicleMove;
import com.neodem.parkingLot.model.board.VehicleNotOnBoardException;
import com.neodem.parkingLot.model.space.Space;

public abstract class GraphicalVehicle extends Vehicle implements GraphicalObject, ImageObserver {

	protected static final int SPACE_WIDTH = 75;

	protected boolean moveable;

	protected GameContext game;

	protected Image fullImage;

	protected Image image = null;

	/**
	 * top left of the graphic
	 */
	protected int locx;

	/**
	 * top left of the graphic
	 */
	protected int locy;

	protected int width;

	protected int height;

	int distanceMoved = 0;

	// max pixels down or right
	int maxForwardDistance = 0;

	// max pixels up or left
	int maxBackwardDistance = 0;

	// offsets for the actual board location on the screen
	protected int boardX = 0;

	protected int boardY = 0;

	public GraphicalVehicle(VehicleID id, Direction orientation, Space startSpace, GameContext game, int size) {
		super(id, orientation, startSpace, size);
		this.game = game;
		this.moveable = false;
		fullImage = loadFullImage();
		if (orientation.equals(Direction.NORTH) || orientation.equals(Direction.SOUTH)) {
			this.width = SPACE_WIDTH;
			this.height = SPACE_WIDTH * size;
		} else {
			this.width = size * SPACE_WIDTH;
			this.height = SPACE_WIDTH;
		}
		updateGraphicLocation();

	}

	protected abstract Image loadFullImage();

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

		// if we are facing north or west, forward and backward
		// are swapped
		if (orientation.equals(Direction.NORTH) || orientation.equals(Direction.WEST)) {
			int temp = maxForward;
			maxForward = maxBackward;
			maxBackward = temp;
		}

		this.maxForwardDistance = SPACE_WIDTH * maxForward;
		this.maxBackwardDistance = SPACE_WIDTH * maxBackward;

		// compute bounding rect (untested)
		int x, y;
		int w, h;

		if (this.orientation.equals(Direction.NORTH) || this.orientation.equals(Direction.SOUTH)) {
			x = locx;
			y = locy - maxBackwardDistance;

			w = SPACE_WIDTH;
			h = maxForwardDistance + maxBackwardDistance + (SPACE_WIDTH * size);
		} else {
			x = locx - maxBackwardDistance;
			y = locy;

			w = maxForwardDistance + maxBackwardDistance + (SPACE_WIDTH * size);
			h = SPACE_WIDTH;
		}

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
		if (this.orientation.equals(Direction.NORTH) || this.orientation.equals(Direction.SOUTH)) {
			if (this.distanceMoved > 0) {
				movedDir = Direction.SOUTH;
			} else {
				movedDir = Direction.NORTH;
				this.distanceMoved = -this.distanceMoved;
			}
		} else {
			if (this.distanceMoved > 0) {
				movedDir = Direction.EAST;
			} else {
				movedDir = Direction.WEST;
				this.distanceMoved = -this.distanceMoved;
			}
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
			updateGraphicLocation();

			// alert the board of the move
			game.sendEvent(GameEvent.GUIMOVE, new VehicleMove(this, movedDir, spacesMoved));

		} else {
			setFrontSpace(this.frontSpace);
			updateGraphicLocation();
		}
	}

	/**
	 * since the vehicle can only move along one axis, we translate only the x
	 * or the y.. not both
	 * 
	 * @param x
	 * @param y
	 */
	public void translate(int x, int y) {
		if (this.orientation.equals(Direction.NORTH) || this.orientation.equals(Direction.SOUTH)) {

			// check limits
			int testy = y + this.distanceMoved;
			// y goes up when we are going down
			if (y > 0) {
				// moving down
				if (testy > this.maxForwardDistance) {
					y = this.maxForwardDistance;
					return;
				}
			} else {
				// moving up
				if (-testy > this.maxBackwardDistance) {
					y = -this.maxBackwardDistance;
					return;
				}
			}

			this.locy += y;
			this.distanceMoved += y;
		} else {

			int testx = x + this.distanceMoved;
			// x goes up when we move right
			if (x > 0) {
				// moving right
				if (testx > this.maxForwardDistance) {
					x = this.maxForwardDistance;
					return;
				}
			} else {
				// moving left
				if (-testx > this.maxBackwardDistance) {
					x = -this.maxForwardDistance;
					return;
				}
			}

			this.locx += x;
			this.distanceMoved += x;
		}
	}

	/**
	 * determine the locx and locy from the frontSpace of the vehicle. (make the
	 * locx and locy == the top left of the vehicle)
	 */
	public void updateGraphicLocation() {
		if (this.frontSpace == null) {
			return;
		}

		int row = this.game.getSpaceModel().getRowNumber(this.frontSpace);
		int col = this.game.getSpaceModel().getColNumber(this.frontSpace);

		this.locx = this.boardX + (col * SPACE_WIDTH);
		this.locy = this.boardY + (row * SPACE_WIDTH);

		// if we face away from the top left, we offset the locx and locy
		if (this.orientation.equals(Direction.SOUTH)) {
			locy = locy - ((size - 1) * SPACE_WIDTH);
		} else if (this.orientation.equals(Direction.EAST)) {
			locx = locx - ((size - 1) * SPACE_WIDTH);
		}
	}

	public void paint(Graphics g) {
		g.drawImage(getVehicleImage(), this.locx, this.locy, this);
	}

	public boolean inside(int x, int y) {
		if (this.locx <= x) {
			if (this.locy <= y) {
				if ((this.locx + this.width) >= x) {
					if ((this.locy + this.height) >= y) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return true;
	}

	public boolean isMoveable() {
		return this.moveable;
	}

	public void setBoardX(int boardX) {
		this.boardX = boardX;
		updateGraphicLocation();
	}

	public void setBoardY(int boardY) {
		this.boardY = boardY;
		updateGraphicLocation();
	}

	/**
	 * return the correct portion of the full image depending on the orientation
	 * 
	 * @param fullImage
	 * @return
	 */
	public Image getVehicleImage() {
		int x = 0;
		int y = 0;
		int w = SPACE_WIDTH;
		int h = SPACE_WIDTH * size;

		if (orientation.equals(Direction.SOUTH)) {
			x = SPACE_WIDTH;
		} else if (orientation.equals(Direction.EAST)) {
			x = SPACE_WIDTH * size;
			w = SPACE_WIDTH * size;
			h = SPACE_WIDTH;
		} else if (orientation.equals(Direction.WEST)) {
			x = SPACE_WIDTH * size;
			y = SPACE_WIDTH;
			w = SPACE_WIDTH * size;
			h = SPACE_WIDTH;
		}

		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buff.createGraphics();

		g2.drawImage(fullImage, 0, 0, w, h, x, y, x + w, y + h, this);

		return buff;
	}

	public void update() {

	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
}
