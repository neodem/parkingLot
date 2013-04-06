
package com.neodem.parkingLot.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.neodem.parkingLot.event.GameEvent;
import com.neodem.parkingLot.event.GameEventListener;
import com.neodem.parkingLot.model.Direction;
import com.neodem.parkingLot.model.VehicleMove;
import com.neodem.parkingLot.model.board.Board;
import com.neodem.parkingLot.model.board.BoardException;
import com.neodem.parkingLot.tracking.History;
import com.neodem.parkingLot.vehicle.GraphicalVehicle;

/**
 * the main class for the app. Holds the main menu and the gamePanel
 * 
 * @author Vince
 * 
 */
public class ParkingLot implements GameEventListener {
	
	private static final String APP_NAME = "Parking Lot";

	/**
	 * the main game
	 */
	private GameContext game;

	private static JFrame mainFrame;

	private GamePanel panel;

	private JMenuItem undo;

	private JMenuItem redo;
	
	private static void createAndShowGUI()  {
		// Create and set up the window.
		mainFrame = new JFrame(APP_NAME);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		ParkingLot game = new ParkingLot();		
		mainFrame.setJMenuBar(game.makeMenuBar(mainFrame));
		mainFrame.getContentPane().add(game.makeConent());
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private Component makeConent() {
		// Create the main game panel
		panel = new GamePanel(getGame());
		return panel;
	}

	private GameContext getGame() {
		return game;
	}

	public static void main(String argv[]) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private ParkingLot() {
		game = new GameContext();
		game.init();
		game.addListener(this);	
	}
	
	public void handleEvent(GameEvent event) {
		if(GameEvent.MOVE_RECORDED.equals(event)) {
			undo.setEnabled(true);
		}
	}
	
	private JMenu makeBoardMenu() {
		JMenu menu = new JMenu("Board");
		menu.setMnemonic('B');
		
		JMenuItem newMenu = new JMenuItem(new NewBoardAction());
		newMenu.setEnabled(false);
		menu.add(newMenu);
		
		JMenuItem save = new JMenuItem(new SaveBoardAction());
		save.setEnabled(false);
		menu.add(save);
		
		JMenuItem load = new JMenuItem(new LoadBoardAction());
		load.setEnabled(false);
		menu.add(load);
		
		JMenuItem record = new JMenuItem(new RecordSolutionAction());
		record.setEnabled(false);
		menu.add(record);
		
		return menu;
	}
	
	private JMenu makeFileMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		JMenuItem newMenu = new JMenuItem(new NewGameAction());
		newMenu.setEnabled(false);
		fileMenu.add(newMenu);
		
		JMenuItem save = new JMenuItem(new SaveGameAction());
		save.setEnabled(false);
		fileMenu.add(save);
		
		JMenuItem load = new JMenuItem(new LoadGameAction());
		load.setEnabled(false);
		fileMenu.add(load);
		
		JMenuItem exit = new JMenuItem(new QuitAction());
		fileMenu.add(exit);
			
		return fileMenu;
	}

	private JMenu makeGameMenu() {
		JMenu menu = new JMenu("Game");
		menu.setMnemonic('G');
		
		undo = new JMenuItem(new UndoGameAction());
		undo.setEnabled(false);
		menu.add(undo);
		
		redo = new JMenuItem(new RedoGameAction());
		redo.setEnabled(false);
		menu.add(redo);
		
		JMenuItem reset = new JMenuItem(new ResetGameAction());
		reset.setEnabled(false);
		menu.add(reset);
		
		JMenuItem show = new JMenuItem(new ShowSolutionAction());
		show.setEnabled(false);
		menu.add(show);
		
		JMenuItem solve = new JMenuItem(new SolveGameAction());
		solve.setEnabled(false);
		menu.add(solve);
		
		
		return menu;
	}

	private JMenu makeHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.setMnemonic('H');
		JMenuItem about = new JMenuItem(new AboutAction());
		menu.add(about);
		return menu;
	}
	
	private JMenuBar makeMenuBar(final Component parent) {
		JMenuBar menu = new JMenuBar();
		menu.add(makeFileMenu());
		menu.add(makeGameMenu());
		menu.add(makeBoardMenu());
		menu.add(makeHelpMenu());

		return menu;
	}
	
	private class AboutAction extends AbstractAction {
	
		public AboutAction() {
			super("About...");
			putValue(SHORT_DESCRIPTION, "About this program");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
		}
	
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(mainFrame, "about text", "About", JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * load a board for editing
	 * @author Vince
	 *
	 */
	private class LoadBoardAction extends AbstractAction {
	
		public LoadBoardAction() {
			super("Load...");
			putValue(SHORT_DESCRIPTION, "Load a board for editing");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_L));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class LoadGameAction extends AbstractAction {
	
		public LoadGameAction() {
			super("Load...");
			putValue(SHORT_DESCRIPTION, "Load a game");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_L));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	/**
	 * create a new board from scratch
	 * @author Vince
	 *
	 */
	private class NewBoardAction extends AbstractAction {
	
		public NewBoardAction() {
			super("New...");
			putValue(SHORT_DESCRIPTION, "Create a new Game board");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_N));
		}
	
		public void actionPerformed(ActionEvent e) {
			// dialog to get game specifics (id, difficulty)
			// 
		}
	}

	/**
	 * allow a person to load a new board into 
	 * a new game
	 * @author Vince
	 *
	 */
	private class NewGameAction extends AbstractAction {
	
		public NewGameAction() {
			super("New...");
			putValue(SHORT_DESCRIPTION, "Start a new Game");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_N));
		}
	
		public void actionPerformed(ActionEvent e) {
			// fire up board chooser
			// load the new game
			// reset display
		}
	}

	private class QuitAction extends AbstractAction {
	
		public QuitAction() {
			super("Exit");
			putValue(SHORT_DESCRIPTION, "Quit the program");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
		}
	
		public void actionPerformed(ActionEvent e) {
			game.quit();
			System.exit(0);
		}
	}

	/**
	 * record a solution for an edited board
	 * @author Vince
	 *
	 */
	private class RecordSolutionAction extends AbstractAction {
	
		public RecordSolutionAction() {
			super("Record");
			putValue(SHORT_DESCRIPTION, "Record a board solution");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class RedoGameAction extends AbstractAction {
	
		public RedoGameAction() {
			super("Redo");
			putValue(SHORT_DESCRIPTION, "Redo the move");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class ResetGameAction extends AbstractAction {
	
		public ResetGameAction() {
			super("Reset");
			putValue(SHORT_DESCRIPTION, "Reset the Game");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	/**
	 * save a newly created board
	 * @author Vince
	 *
	 */
	private class SaveBoardAction extends AbstractAction {
	
		public SaveBoardAction() {
			super("Save");
			putValue(SHORT_DESCRIPTION, "Save the board");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class SaveGameAction extends AbstractAction {
	
		public SaveGameAction() {
			super("Save");
			putValue(SHORT_DESCRIPTION, "Save a game");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class ShowSolutionAction extends AbstractAction {
	
		public ShowSolutionAction() {
			super("Show Solution");
			putValue(SHORT_DESCRIPTION, "Display the game solution");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_W));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class SolveGameAction extends AbstractAction {
	
		public SolveGameAction() {
			super("Reset");
			putValue(SHORT_DESCRIPTION, "Solve the Game");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
		}
	
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class UndoGameAction extends AbstractAction {
	
		public UndoGameAction() {
			super("Undo");
			putValue(SHORT_DESCRIPTION, "Undo the move");
			putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_Z));
		}
	
		public void actionPerformed(ActionEvent e) {
			History history = game.getHistory();
			VehicleMove move = history.getLastMove();
			move.setMovedDir(move.getMovedDir().oppositeDirection());
			
			// update the board
			Board board = game.getBoard();
			boolean result = false;
			try {
				result = board.moveVehicle(move);
			} catch (BoardException e1) {
				e1.printStackTrace();
			}
			
			// update the graphics
			if(result == true) {
				((GraphicalVehicle)move.getVehicle()).updateGraphicLocation();
				game.sendEvent(GameEvent.REDRAW_BOARD);
			}
		}
	}
}
