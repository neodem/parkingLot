
package com.neodem.parkingLot.loader.impl;

import java.io.File;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.neodem.parkingLot.model.setup.BoardBean;
import com.neodem.parkingLot.model.setup.GameSet;
import com.neodem.parkingLot.model.setup.SolutionBean;
import com.neodem.parkingLot.model.setup.VehicleSetupBean;
import com.neodem.parkingLot.file.Difficulty;

public class XMLFileLoader {

	public static GameSet loadFile(File xmlFile) {
		Builder parser = new Builder();
		Document doc = null;
		try {
			doc = parser.build(xmlFile);
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getBoards(doc);
	}

	private static GameSet getBoards(Document document) {
		GameSet set = new GameSet();

		if (document != null) {
			Element root = document.getRootElement();
			Elements children = root.getChildElements();

			for (int i = 0; i < children.size(); i++) {
				Element board = children.get(i);
				
				String difficultyString = board.getAttributeValue("difficulty");
				String idString = board.getAttributeValue("id");
				
				Difficulty diff = Difficulty.getDifficulty(difficultyString);
				
				BoardBean bean = new BoardBean(idString, diff);

				Element setupElement = board.getFirstChildElement("setup");
				Elements vehicles = setupElement.getChildElements();

				for (int j = 0; j < vehicles.size(); j++) {
					Element vehicle = vehicles.get(j);
					String type = vehicle.getAttributeValue("type");
					String id = vehicle.getAttributeValue("vehicleID");
					String facing = vehicle.getAttributeValue("facing");
					String start = vehicle.getAttributeValue("start");
					bean.addVehicle(new VehicleSetupBean(type, id, facing, start));
				}

				Element solutionElement = board.getFirstChildElement("solution");
				Elements moves = solutionElement.getChildElements();

				for (int j = 0; j < moves.size(); j++) {
					Element move = moves.get(j);
					String number = move.getAttributeValue("number");
					String id = move.getAttributeValue("vehicleID");
					String facing = move.getAttributeValue("direction");
					String distance = move.getAttributeValue("distance");
					bean.addSoution(new SolutionBean(number, id, facing, distance));
				}

				set.addBoardBean(bean);
			}
		}

		return set;
	}
}
