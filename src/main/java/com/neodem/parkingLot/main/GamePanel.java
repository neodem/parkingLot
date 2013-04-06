package com.neodem.parkingLot.main;

import javax.swing.JPanel;

/**
 * whill hold the boardPanel and any associated
 * displays and screen buttons
 * @author Vince
 *
 */
public class GamePanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6297565722429781004L;

	public GamePanel(GameContext game) {
		super();
		this.add(new BoardPanel(game));
	}
}
