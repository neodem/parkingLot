package com.neodem.parkingLot.model;

import com.neodem.parkingLot.model.space.SpaceModel;

import junit.framework.TestCase;

public class SpaceModelTest extends TestCase {
//
//	public void testRowMin() {
//		assertEquals(0, model.rowMin(0));
//		assertEquals(4, model.rowMin(1));
//		assertEquals(8, model.rowMin(2));
//		assertEquals(12, model.rowMin(3));
//	}
//	
//	public void testRowMax() {
//		assertEquals(3, model.rowMax(0));
//		assertEquals(7, model.rowMax(1));
//		assertEquals(11, model.rowMax(2));
//		assertEquals(15, model.rowMax(3));
//	}
//	
	
	///////////////////////////////
	
	SpaceModel model;
	
	public SpaceModelTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		model = new SpaceModel(4);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model = null;
	}
}
