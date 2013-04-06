
package com.neodem.parkingLot.vehicle;

import java.awt.Image;
import java.awt.Toolkit;

import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.space.Space;

/**
 * Will hold all the relavant data for a car in the game
 * 
 * @author Vince
 */
public class GraphicalCar extends GraphicalVehicle {


	public GraphicalCar(VehicleID id, Direction orientation, Space startSpace, GameContext game) {
		super(id, orientation, startSpace, game, 2);
	}
	
	protected Image loadFullImage() {
		String filename = "resources/graphics/cars-" + id.getId() + ".png";
		return Toolkit.getDefaultToolkit().getImage(filename);
	}
	
//	protected Image loadFullImage() {
//		String idString = this.id.getId();
//		int size = SPACE_WIDTH * getSize() * 3;
//
//		BufferedImage buff = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2 = buff.createGraphics();
//
//		g2.setColor(Color.yellow);
//		g2.fillRect(0, 0, size, size);
//
//		return buff;
//	}
}
