
package com.neodem.parkingLot.vehicle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.neodem.parkingLot.main.GameContext;
import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.space.Space;

public class GraphicalTruck extends GraphicalVehicle {

	public GraphicalTruck(VehicleID id, Direction orientation, Space startSpace, GameContext game) {
		super(id, orientation, startSpace, game, 3);
	}

	protected Image loadFullImage() {
		String idString = this.id.getId();
		this.color = getColor(idString);
		int size = SPACE_WIDTH * getSize() * 3;

		BufferedImage buff = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buff.createGraphics();

		g2.setColor(color);
		g2.fillRect(0, 0, size, size);

		return buff;
	}

	private Color color;

	/**
	 * temp to assign colors to the vehicles..
	 * 
	 * @param vehicleID
	 * @return
	 */
	private Color getColor(String id) {

		if ("B".equals(id)) {
			return Color.orange;
		}
		if ("C".equals(id)) {
			return Color.magenta;
		}
		if ("O".equals(id)) {
			return Color.yellow;
		}
		if ("P".equals(id)) {
			return Color.blue;
		}
		if ("Q".equals(id)) {
			return Color.cyan;
		}
		if ("R".equals(id)) {
			return Color.green;
		}
		return Color.blue;
	}

}
