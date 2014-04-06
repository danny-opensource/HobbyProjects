package com.zsrm.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * TasksList holds all tasks created by the user. It holds the complete list
 * 
 * @author Janani V.
 * 
 */
public class TasksList {

	private static ArrayList<Task> tasksList = new ArrayList<Task>();

	/**
	 * Add a task item to the list
	 * 
	 * @param t
	 *            is the Task
	 */
	public static void addTaskItem(Task t) {
		tasksList.add(t);
	}

	/**
	 * Return the TasksList
	 * 
	 * @return tasksList
	 */
	public static ArrayList<Task> getTasksList() {
		return tasksList;
	}

}
