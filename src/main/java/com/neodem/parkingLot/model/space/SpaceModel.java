package com.neodem.parkingLot.model.space;

import java.util.Iterator;
import java.util.List;

import com.neodem.parkingLot.model.Direction;

public class SpaceModel implements Iterable {

	class SpaceIterator implements Iterator {
		int count = 0;

		public boolean hasNext() {
			if (count < fullsize)
				return true;
			return false;
		}

		public Object next() {
			Space space = new Space(count);
			count++;
			return space;
		}

		public void remove() {

		}
	}

	public Iterator iterator() {
		return new SpaceIterator();
	}

	public static Space makeSpace(int spaceNumber) {
		return new Space(spaceNumber);
	}

	/**
	 * this is the number of cols and rows on the board
	 */
	public static final int SIZE = 6;

	public static final int FULLSIZE = SIZE * SIZE;

	private int size = SIZE;

	private int fullsize = FULLSIZE;

	private SpaceModel() {
	}

	public SpaceModel(int size) {
		this.size = size;
		this.fullsize = size * size;
	}

	/**
	 * return the space numbers for a given direction and distance (including the starting space)
	 * 
	 * the path may include wall spaces
	 * 
	 * @param startSpace
	 * @param direction
	 * @param distance
	 * @return null if neg or 0 distance
	 */
	public Space[] determinePath(Space startSpace, Direction direction, int distance) {
		if (distance == 0)
			return null;
		if (distance < 0)
			return null;
		
		int pathDistance = distance + 1;

		Space[] path = new Space[pathDistance];
		for (int i = 0; i < pathDistance; i++) {
			Space space = getSpace(startSpace, direction, i);
			if (space == null) {
				return null;
			}
			// space may be a wall
			path[i] = space;
		}

		return path;
	}

	public boolean isLastInRow(Space space) {
		int row = getRowNumber(space);

		int rowMax = rowMax(row);
		if (rowMax == space.getAbsoluteSpace())
			return true;
		return false;
	}

	/**
	 * given a list of spaces, return the one that is the most in the given
	 * direction (assumes that the list is composed of elements that are in a
	 * line.. ie. if we line them up they would face in the diection)
	 * 
	 * @param spaceList
	 * @param direction
	 * @return
	 */
	public Space getDirectionMostSpaceFromList(List<Space> spaceList, Direction direction) {
		int max = -1;
		int min = fullsize + 1;
		for (Space space : spaceList) {
			int val = space.getAbsoluteSpace();
			if (val > max)
				max = val;
			if (val < min)
				min = val;
		}

		if ((max == -1) || (min == fullsize + 1)) {
			return null;
		}

		if ((direction.equals(Direction.NORTH)) || (direction.equals(Direction.WEST))) {
			return new Space(min);
		}

		return new Space(max);
	}

	/**
	 * detemine the fullsize of the board.
	 * @return a count of all the spaces on the board
	 */
	public int getNumberOfSpaces() {
		return fullsize;
	}

	/**
	 * detemine the length of a row/or col ofthe board.
	 * @return a count of the number of spaces on the board
	 */
	public int getRowLength() {
		return size;
	}

	public int getRowNumber(Space space) {
		return space.getAbsoluteSpace() / size;
	}
	

	public int getColNumber(Space space) {
		int row = getRowNumber(space);
		return space.getAbsoluteSpace() - rowMin(row);
	}

	/**
	 * return the maxvalue for this row
	 * 
	 * @param row
	 *            number
	 * @return
	 */
	protected int rowMax(int row) {
		return (row * size) + (size - 1);
	}

	/**
	 * return the minvalue for this row
	 * 
	 * @param row
	 *            number
	 * @return
	 */
	protected int rowMin(int row) {
		return (row * size);
	}

	/**
	 * return the space number that is a given direction and distance from the
	 * start space
	 * 
	 * @param startSpace
	 *            (may not be a wall)
	 * @param direction
	 * @param distance
	 * @return null if this is a wall or startSpace not on board
	 */
	public Space getSpace(Space startingSpace, Direction direction, int distance) {
		if (startingSpace == null)
			return null;
		if (startingSpace.isWall())
			return null;

		int startSpace = startingSpace.getAbsoluteSpace();

		if ((startSpace < 0) || (startSpace >= fullsize)) {
			return null;
		}

		if (distance < 0)
			return null;

		if (distance == 0)
			return startingSpace;

		int space = startSpace;

		if (direction.equals(Direction.NORTH)) {
			space = space - (size * distance);
			if (space < 0)
				return new Wall();
		}

		else if (direction.equals(Direction.SOUTH)) {
			space = space + (size * distance);
			if (space > (fullsize - 1))
				return new Wall();
		}

		else if (direction.equals(Direction.EAST)) {
			space = space + distance;
			if (space > rowMax(getRowNumber(startingSpace)))
				return new Wall();
		}

		else if (direction.equals(Direction.WEST)) {
			space = space - distance;
			if (space < rowMin(getRowNumber(startingSpace)))
				return new Wall();
		}

		return new Space(space);
	}

}
