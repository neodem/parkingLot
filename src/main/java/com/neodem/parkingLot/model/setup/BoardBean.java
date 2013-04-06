package com.neodem.parkingLot.model.setup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.neodem.parkingLot.file.Difficulty;

/**
 * represents a full game instance: board and solution.
 * @author Vince
 *
 */
public class BoardBean {
	private Difficulty difficulty;
	private Integer id;
	private Set<VehicleSetupBean> setups = new HashSet<VehicleSetupBean>();
	private List<SolutionBean> solutions = new ArrayList<SolutionBean>();

	
	public BoardBean(Integer id, Difficulty difficulty) {
		this.id = id;
		this.difficulty = difficulty;
	}
	
	public BoardBean(int id, Difficulty difficulty) {
		this.id = new Integer(id);
		this.difficulty = difficulty;
	}
	
	public BoardBean(String idString, Difficulty difficulty) {
		if(StringUtils.isBlank(idString)) {
			throw new IllegalArgumentException("idString may not be blank");
		} else {
			this.id = new Integer(idString);
		}
		this.difficulty = difficulty;
	}

	
	/**
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the setups
	 */
	public Set<VehicleSetupBean> getSetups() {
		return setups;
	}

	/**
	 * @param setups the setups to set
	 */
	public void setSetups(Set<VehicleSetupBean> setups) {
		this.setups = setups;
	}

	/**
	 * @return the solutions
	 */
	public List<SolutionBean> getSolutions() {
		return solutions;
	}
	
	/**
	 * @param solutions the solutions to set
	 */
	public void setSolutions(List<SolutionBean> solutions) {
		this.solutions = solutions;
	}

	public void addVehicle(VehicleSetupBean bean) {
		setups.add(bean);
	}

	/**
	 * @param bean
	 */
	public void addSoution(SolutionBean bean) {
		solutions.add(bean);	
	}

}
