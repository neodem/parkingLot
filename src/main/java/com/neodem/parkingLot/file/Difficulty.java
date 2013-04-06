/**
 * 
 */
package com.neodem.parkingLot.file;


/**
 * @author Vince
 *
 */
public enum Difficulty implements GameFile {
	EASY, MEDIUM, HARD, VERYHARD;

	public static Difficulty getDifficulty(String difficultyString) {
		if(D_EASY.equalsIgnoreCase(difficultyString)) {
			return EASY;
		}
		if(D_MED.equalsIgnoreCase(difficultyString)) {
			return MEDIUM;
		}
		if(D_HARD.equalsIgnoreCase(difficultyString)) {
			return HARD;
		}
		if(D_VHARD.equalsIgnoreCase(difficultyString)) {
			return VERYHARD;
		}
		return null;
	}
}
