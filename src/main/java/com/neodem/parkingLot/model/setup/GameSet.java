
package com.neodem.parkingLot.model.setup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.neodem.parkingLot.file.Difficulty;

/**
 * contains a set of BoardBean objects and
 * has accessors for them by id and difficulty
 * @author Vince
 *
 */
public class GameSet {

	private static final Random ran = new Random(System.currentTimeMillis());

	private Map<Integer, BoardBean> beans = new HashMap<Integer, BoardBean>();

	private Map<Difficulty, Set<Integer>> byDifficulty = new HashMap<Difficulty, Set<Integer>>();

	public void addBoardBean(BoardBean boardBean) {
		if (boardBean == null)
			return;
		Integer id = boardBean.getId();
		beans.put(id, boardBean);

		Difficulty diff = boardBean.getDifficulty();
		Set<Integer> ids = byDifficulty.get(diff);
		if (ids == null) {
			ids = new HashSet<Integer>();
		}
		ids.add(id);
		byDifficulty.put(diff, ids);
	}

	public BoardBean getBoard(Integer id) {
		return beans.get(id);
	}

	public BoardBean getRandomBoard(Difficulty difficulty) {
		Set<Integer> ids = byDifficulty.get(difficulty);
		if ((ids == null) || (ids.size() == 0)) {
			return null;
		}
		int id = getRandom(1, ids.size());
		return getBoard(new Integer(id));
	}

	/**
	 * return a random number from one int to another
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private int getRandom(int i, int j) {
		if (i == j)
			return i;
		if (i > j) {
			return ran.nextInt(i+1) + j;
		}
		return ran.nextInt(j+1) + i;
	}
}
