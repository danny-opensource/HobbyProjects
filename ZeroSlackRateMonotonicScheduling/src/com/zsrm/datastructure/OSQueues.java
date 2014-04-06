package com.zsrm.datastructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Queues for preemption. Called during different phases of exectution of the
 * task
 * 
 * @author Janani V.
 * 
 */
public class OSQueues {

	/**
	 * Running Queue holding all the running tasks
	 */
	public static Queue<Task> runningQueue = new LinkedList<Task>();

	/**
	 * Waiting Queue holding all the tasks to be executed when it gets the turn
	 * while it got preempted previously
	 */
	public static Queue<Task> waitingQueue = new LinkedList<Task>();

	/**
	 * Ready Queue holding all the tasks to be executed during the next cycle
	 */
	public static Queue<Task> readyQueue = new LinkedList<Task>();

}
