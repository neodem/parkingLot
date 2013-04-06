package com.neodem.parkingLot.model;

import java.util.List;
import java.util.Map;

import com.neodem.parkingLot.model.board.BoardException;
import com.neodem.parkingLot.vehicle.GraphicalCar;
import com.neodem.parkingLot.vehicle.GraphicalTruck;
import com.neodem.parkingLot.vehicle.Vehicle;

import junit.framework.TestCase;

public class BoardTest extends TestCase {

	public BoardTest(String name) {
		super(name);
	}

//	public void testDeterminePath() {
//		int[] path = board.determinePath(9, Direction.NORTH, 1);
//		assertEquals(1, path.length);
//		assertEquals(5, path[0]);
//
//		path = board.determinePath(9, Direction.NORTH, 2);
//		assertEquals(2, path.length);
//		assertEquals(5, path[0]);
//		assertEquals(1, path[1]);
//	}
//
//	public void testMoveVehicle() throws BoardException {
//		// move a non registered vehicle
//		Vehicle car = new Car(board, Direction.NORTH, '1');
//		assertFalse(board.moveVehicle(car, Direction.NORTH));
//
//		// move a null vehicle
//		assertFalse(board.moveVehicle(null, Direction.NORTH));
//
//		// make a noth facing car.. with its front on spot 9
//		Vehicle vehicle = new Car(board, Direction.NORTH, '2');
//		vehicle.setFrontSpace(9);
//		assertTrue(board.addVehicle(vehicle));
//
//		Map<Integer, Vehicle> spaceList = board.getSpaceMap();
//		assertEquals(vehicle, spaceList.get(9));
//		assertEquals(vehicle, spaceList.get(13));
//
//		// move the vehicle with null dir
//		assertFalse(board.moveVehicle(vehicle, null));
//
//		// attempt to move the vehicle EAST and WEST
//		assertFalse(board.moveVehicle(vehicle, Direction.EAST));
//		assertFalse(board.moveVehicle(vehicle, Direction.WEST));
//
//		// attempt to move the vehicle down one space
//		assertFalse(board.moveVehicle(vehicle, Direction.SOUTH, 1));
//
//		// move the vehicle up one space
//		assertTrue(board.moveVehicle(vehicle, Direction.NORTH, 1));
//
//		spaceList = board.getSpaceMap();
//		assertEquals(vehicle, spaceList.get(5));
//		assertEquals(vehicle, spaceList.get(9));
//		assertNull(spaceList.get(13));
//
//		assertEquals(5, vehicle.frontSpace);
//		assertEquals(9, vehicle.backSpace);
//
//		// move the vehicle up one space
//		assertTrue(board.moveVehicle(vehicle, Direction.NORTH));
//
//		// move the vehicle down two spaces
//		assertTrue(board.moveVehicle(vehicle, Direction.SOUTH, 2));
//
//		spaceList = board.getSpaceMap();
//		assertEquals(vehicle, spaceList.get(9));
//		assertEquals(vehicle, spaceList.get(13));
//		assertNull(spaceList.get(5));
//		assertNull(spaceList.get(1));
//
//		assertEquals(9, vehicle.frontSpace);
//		assertEquals(13, vehicle.backSpace);
//
//		// move the vehicle up one space
//		assertTrue(board.moveVehicle(vehicle, Direction.NORTH));
//
//	}
//
//	public void testAddVehicle() {
//		assertFalse(board.addVehicle(null));
//
//		// make a noth facing car.. with its front on spot 0
//		Vehicle vehicle = new Car(board, Direction.NORTH);
//		vehicle.setFrontSpace(0);
//		assertTrue(board.addVehicle(vehicle));
//
//		List<Vehicle> spaceList = board.getSpaceMap();
//		assertEquals(vehicle, spaceList.get(0));
//		assertEquals(vehicle, spaceList.get(4));
//
//		// make another vehicle that overlapps
//		Vehicle truck = new Truck(board, Direction.WEST);
//		truck.setFrontSpace(0);
//		assertFalse(board.addVehicle(truck));
//	}
//
//	public void testVehicleInit() {
//		List<Vehicle> spaceList = board.getSpaceMap();
//		for (Vehicle vehicle : spaceList) {
//			assertNull(vehicle);
//		}
//	}
//
//	public void testRowMax() {
//
//		board = new OldBoard(2);
//		assertEquals(-1, board.rowMax(-1));
//		assertEquals(-1, board.rowMax(4));
//		assertEquals(1, board.rowMax(0));
//		assertEquals(1, board.rowMax(1));
//		assertEquals(3, board.rowMax(2));
//		assertEquals(3, board.rowMax(3));
//
//		board = new OldBoard(3);
//		assertEquals(2, board.rowMax(0));
//		assertEquals(2, board.rowMax(1));
//		assertEquals(2, board.rowMax(2));
//		assertEquals(5, board.rowMax(3));
//		assertEquals(5, board.rowMax(4));
//		assertEquals(5, board.rowMax(5));
//		assertEquals(8, board.rowMax(6));
//		assertEquals(8, board.rowMax(7));
//		assertEquals(8, board.rowMax(8));
//
//		board = new OldBoard(4);
//		assertEquals(3, board.rowMax(0));
//		assertEquals(3, board.rowMax(1));
//		assertEquals(3, board.rowMax(2));
//		assertEquals(3, board.rowMax(3));
//		assertEquals(7, board.rowMax(4));
//		assertEquals(7, board.rowMax(5));
//		assertEquals(7, board.rowMax(6));
//		assertEquals(7, board.rowMax(7));
//	}
//
//	public void testRowMin() {
//
//		board = new OldBoard(2);
//		assertEquals(-1, board.rowMin(-1));
//		assertEquals(-1, board.rowMin(4));
//		assertEquals(0, board.rowMin(0));
//		assertEquals(0, board.rowMin(1));
//		assertEquals(2, board.rowMin(2));
//		assertEquals(2, board.rowMin(3));
//
//		board = new OldBoard(3);
//		assertEquals(0, board.rowMin(0));
//		assertEquals(0, board.rowMin(1));
//		assertEquals(0, board.rowMin(2));
//		assertEquals(3, board.rowMin(3));
//		assertEquals(3, board.rowMin(4));
//		assertEquals(3, board.rowMin(5));
//		assertEquals(6, board.rowMin(6));
//		assertEquals(6, board.rowMin(7));
//		assertEquals(6, board.rowMin(8));
//
//		board = new OldBoard(4);
//		assertEquals(0, board.rowMin(0));
//		assertEquals(0, board.rowMin(1));
//		assertEquals(0, board.rowMin(2));
//		assertEquals(0, board.rowMin(3));
//		assertEquals(4, board.rowMin(4));
//		assertEquals(4, board.rowMin(5));
//		assertEquals(4, board.rowMin(6));
//		assertEquals(4, board.rowMin(7));
//	}
//
//	public void testGetSpaceNumber() {
//		board = new OldBoard(3);
//
//		// test out of bounds
//		assertEquals(-1, board.getSpaceNumber(-1, Direction.NORTH, 1));
//		assertEquals(-1, board.getSpaceNumber(9, Direction.NORTH, 1));
//
//		// test ups from top row
//		assertEquals(-1, board.getSpaceNumber(0, Direction.NORTH, 1));
//		assertEquals(-1, board.getSpaceNumber(0, Direction.NORTH, 2));
//		assertEquals(0, board.getSpaceNumber(0, Direction.NORTH, 0));
//		assertEquals(-1, board.getSpaceNumber(0, Direction.NORTH, -1));
//		assertEquals(-1, board.getSpaceNumber(1, Direction.NORTH, 1));
//		assertEquals(-1, board.getSpaceNumber(2, Direction.NORTH, 1));
//
//		// test downs from bottom row
//		assertEquals(-1, board.getSpaceNumber(6, Direction.SOUTH, 1));
//		assertEquals(-1, board.getSpaceNumber(6, Direction.SOUTH, 2));
//		assertEquals(6, board.getSpaceNumber(6, Direction.SOUTH, 0));
//		assertEquals(-1, board.getSpaceNumber(6, Direction.SOUTH, -1));
//		assertEquals(-1, board.getSpaceNumber(7, Direction.SOUTH, 1));
//		assertEquals(-1, board.getSpaceNumber(8, Direction.SOUTH, 1));
//
//		// test rights from right column
//		assertEquals(-1, board.getSpaceNumber(2, Direction.EAST, 1));
//		assertEquals(-1, board.getSpaceNumber(2, Direction.EAST, 2));
//		assertEquals(2, board.getSpaceNumber(2, Direction.EAST, 0));
//		assertEquals(-1, board.getSpaceNumber(2, Direction.EAST, -1));
//		assertEquals(-1, board.getSpaceNumber(5, Direction.EAST, 1));
//		assertEquals(-1, board.getSpaceNumber(8, Direction.EAST, 1));
//
//		// test left from left column
//		assertEquals(-1, board.getSpaceNumber(0, Direction.WEST, 1));
//		assertEquals(-1, board.getSpaceNumber(0, Direction.WEST, 2));
//		assertEquals(0, board.getSpaceNumber(0, Direction.WEST, 0));
//		assertEquals(-1, board.getSpaceNumber(0, Direction.WEST, -1));
//		assertEquals(-1, board.getSpaceNumber(3, Direction.WEST, 1));
//		assertEquals(-1, board.getSpaceNumber(6, Direction.WEST, 1));
//
//		// test ups from bottom row
//		assertEquals(3, board.getSpaceNumber(6, Direction.NORTH, 1));
//		assertEquals(0, board.getSpaceNumber(6, Direction.NORTH, 2));
//		assertEquals(-1, board.getSpaceNumber(6, Direction.NORTH, 3));
//		assertEquals(4, board.getSpaceNumber(7, Direction.NORTH, 1));
//		assertEquals(5, board.getSpaceNumber(8, Direction.NORTH, 1));
//
//		// test downs from top row
//		assertEquals(3, board.getSpaceNumber(0, Direction.SOUTH, 1));
//		assertEquals(6, board.getSpaceNumber(0, Direction.SOUTH, 2));
//		assertEquals(-1, board.getSpaceNumber(0, Direction.SOUTH, 3));
//		assertEquals(4, board.getSpaceNumber(1, Direction.SOUTH, 1));
//		assertEquals(5, board.getSpaceNumber(2, Direction.SOUTH, 1));
//
//		// test rights from left column
//		assertEquals(1, board.getSpaceNumber(0, Direction.EAST, 1));
//		assertEquals(2, board.getSpaceNumber(0, Direction.EAST, 2));
//		assertEquals(-1, board.getSpaceNumber(0, Direction.EAST, 3));
//		assertEquals(4, board.getSpaceNumber(3, Direction.EAST, 1));
//		assertEquals(8, board.getSpaceNumber(7, Direction.EAST, 1));
//
//		// test left from right column
//		assertEquals(1, board.getSpaceNumber(2, Direction.WEST, 1));
//		assertEquals(0, board.getSpaceNumber(2, Direction.WEST, 2));
//		assertEquals(-1, board.getSpaceNumber(2, Direction.WEST, 3));
//		assertEquals(4, board.getSpaceNumber(5, Direction.WEST, 1));
//		assertEquals(7, board.getSpaceNumber(8, Direction.WEST, 1));
//
//	}
//
//	OldBoard board;
//
//	protected void setUp() throws Exception {
//		board = new OldBoard(4);
//	}
//
//	protected void tearDown() throws Exception {
//		board = null;
//	}

}
