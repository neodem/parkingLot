
package com.neodem.parkingLot.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.event.GameEventListener;
import com.neodem.parkingLot.model.board.VehicleNotOnBoardException;
import com.neodem.parkingLot.model.space.Space;
import com.neodem.parkingLot.vehicle.GraphicalVehicle;
import com.neodem.parkingLot.vehicle.KeyCar;
import com.neodem.parkingLot.vehicle.Vehicle;
import com.neodem.parkingLot.vehicle.VehicleID;

/**
 * this is the panel that holds the board and the vehicles and
 * deals with movement of the vehicles.
 * 
 * @author Vince
 *
 */
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener, GameEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5856885031606371980L;

	protected static final int SQUARE_SIZE = 75;

	protected static final int BOARD_OFFSET_X = 25;

	protected static final int BOARD_OFFSET_Y = 25;

	private Image background;

	private GameContext game;

	protected int boardSize = 6;

	protected int oldx, oldy;

	private Map<VehicleID, Vehicle> vehicles;

	private Set<GraphicalVehicle> sprites;

	private VehicleID keyID = null;

	private GraphicalVehicle movingVehicle = null;

	public BoardPanel(GameContext game) {
		super();
		
		this.background = Toolkit.getDefaultToolkit().getImage("resources/graphics/test.png");
		this.setPreferredSize(new Dimension(500, 500));
	
		this.game = game;
		game.addListener(this);
		this.boardSize = game.getBoardSize();
	
		// get the set of vehicles
		this.vehicles = this.game.getVehicles();

		// use that set to create the set of car and truck sprites
		setupSprites(this.vehicles);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(this.background, 0, 0, this);

		for (GraphicalVehicle gv : this.sprites) {
			gv.paint(g);
		}
	}

	private GraphicalVehicle findOwner(int x, int y) {
		for (GraphicalVehicle vehicle : this.sprites) {
			if (vehicle.inside(x, y)) {
				return vehicle;
			}
		}
		return null;
	}

	protected boolean isWon() {
		Vehicle v = this.vehicles.get(this.keyID);
		if (v == null) {
			return false;
		}
		Space space = null;
		try {
			space = this.game.getBoard().getFrontLocation(v);
		} catch (VehicleNotOnBoardException e) {
			e.printStackTrace();
			return false;
		}
		if (space.equals(GameContext.getWinningSpace())) {
			return true;
		}
		return false;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (this.movingVehicle != null) {
			if (this.movingVehicle.isMoveable()) {
				int x = e.getX();
				int y = e.getY();
				this.movingVehicle.translate(x - this.oldx, y - this.oldy);
				this.oldx = x;
				this.oldy = y;
			}
		}

		// TODO add bounding box here : determine max size of move
		// and then only repaint that area
		this.repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		this.movingVehicle = findOwner(x, y);
		if (this.movingVehicle != null) {
			this.oldx = e.getX();
			this.oldy = e.getY();
			try {
				this.movingVehicle.startMove();
			} catch (VehicleNotOnBoardException e1) {
				return;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (this.movingVehicle != null) {
			this.movingVehicle.endMove();
			this.movingVehicle = null;
		}

		if (isWon()) {
			game.sendEvent(GameEvent.GAME_WON);
		}

		this.repaint();
	}

	private void setupSprites(Map<VehicleID, Vehicle> vehicles) {
		this.sprites = new HashSet<GraphicalVehicle>();
		Set<VehicleID> ids = vehicles.keySet();
		for (VehicleID id : ids) {
			Vehicle vehicle = vehicles.get(id);
			if (vehicle instanceof GraphicalVehicle) {
				GraphicalVehicle v = (GraphicalVehicle) vehicle;
				v.setBoardX(BOARD_OFFSET_X);
				v.setBoardY(BOARD_OFFSET_Y);
				this.sprites.add(v);
			}
			if (vehicle instanceof KeyCar) {
				this.keyID = vehicle.getId();
			}
		}
	}

	public void handleEvent(GameEvent event) {
		if(GameEvent.REDRAW_BOARD.equals(event)) {
			this.repaint();
		}
	}

}
