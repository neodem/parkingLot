package com.neodem.parkingLot.model;

import java.awt.Graphics;

public interface GraphicalObject {
	public abstract void paint(Graphics g);
	public abstract void update();
}
