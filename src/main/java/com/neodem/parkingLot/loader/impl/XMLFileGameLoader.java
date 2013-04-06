package com.neodem.parkingLot.loader.impl;

import java.io.File;

public class XMLFileGameLoader extends AbstractGameLoader {

	private File file;

	public XMLFileGameLoader(File file) {
		this.file = file;
	}
	
	@Override
	protected boolean loadGameSet() {
		gameSet = XMLFileLoader.loadFile(file);
		return true;
	}
}
