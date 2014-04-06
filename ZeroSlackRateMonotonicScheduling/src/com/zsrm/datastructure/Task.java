package com.zsrm.datastructure;

/**
 * This class is the blueprint of the Task. Task defines the data structure for
 * an exectutable task
 * 
 * @author Janani V
 * 
 */
public class Task implements Comparable {

	private String taskName = null;
	private int execTime = 0;
	private int overloadedExecTime = 0;
	private int period = 0;
	private int criticality = 0;
	private int priority = 0;
	private int zeroSlackInstant = 0;
	private int cyclesAssisgned = 0;
	private int remainingTime = 0;
	private int round = 1;

	public Task(String taskName, int execTime, int overloadedExecTime, int period, int criticiality, int priority, int zeroSlackInstant,
			int cyclesAssigned) {
		this.taskName = taskName;
		this.execTime = execTime;
		this.overloadedExecTime = overloadedExecTime;
		this.period = period;
		this.criticality = criticiality;
		this.priority = priority;
		this.zeroSlackInstant = zeroSlackInstant;
		this.cyclesAssisgned = cyclesAssigned;
		this.remainingTime = overloadedExecTime;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getRound() {
		return round;
	}

	public void setRemainingTime(int time) {
		remainingTime = time;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	/**
	 * Returns the assigned task name. Null not allowed in this case
	 * 
	 * @return taskName;
	 */
	public String getTaskName() {
		return this.taskName;
	}

	public int getExecTime() {
		return execTime;
	}

	public void setExecTime(int exec) {
		execTime = exec;
	}

	public int getOverloadedExecTime() {
		return overloadedExecTime;
	}

	public void setOverloadedExecTime(int overloadedExecTime) {
		this.overloadedExecTime = overloadedExecTime;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getCriticality() {
		return criticality;
	}

	public void setCriticality(int criticality) {
		this.criticality = criticality;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getZeroSlackInstant() {
		return zeroSlackInstant;
	}

	public void setZeroSlackInstant(int zeroSlackInstant) {
		this.zeroSlackInstant = zeroSlackInstant;
	}

	public int getCyclesAssisgned() {
		return cyclesAssisgned;
	}

	public void setCyclesAssisgned(int cyclesAssisgned) {
		this.cyclesAssisgned = cyclesAssisgned;
	}

	@Override
	public int compareTo(Object arg0) {
		Task compareObj = (Task) arg0;
		if (this.period > compareObj.period) {
			return 1;
		} else if (this.period == compareObj.period)
			return 0;
		return -1;
	}

	@Override
	public String toString() {

		return taskName + "\t" + "C: " + execTime + "\t" + "C0: " + overloadedExecTime + "\t" + "T: " + period + "\tCri: " + criticality + "\tPri: "
				+ priority;
	}

}
