package com.zsrm.utils;

import java.util.ArrayList;

import com.zsrm.datastructure.OSQueues;
import com.zsrm.datastructure.Task;
import com.zsrm.datastructure.TasksList;

/**
 * Add all the utility methods used by various modules in this utility class
 * 
 * @author Janani V.
 * 
 */
public class GeneralUtils {

	/**
	 * Utility method to update ZeroSlackInstant values for all the tasks
	 */
	public static void updateZSI() {
		ArrayList<Task> tasksList = TasksList.getTasksList();

		for (int i = 0; i < tasksList.size(); i++) {
			Task task = tasksList.get(i);
			task.setZeroSlackInstant((task.getRound() * task.getPeriod()) - task.getOverloadedExecTime() + task.getCyclesAssisgned());
		}
	}

	public static Task getHighestPriorityTask() {
		Task highestPrioTask = null;
		ArrayList<Task> tasksList = TasksList.getTasksList();
		int counter = 0;
		/*for (int i = 0; i < tasksList.size(); i++) {
			Task task = tasksList.get(i);
			if (highestPrioTask == null) {
				highestPrioTask = task;
				if (highestPrioTask.getCyclesAssisgned() == highestPrioTask.getOverloadedExecTime()) {
					highestPrioTask = null;
				}
			} else {
				if ((task.getPriority() <= highestPrioTask.getPriority())
						&& (highestPrioTask.getCyclesAssisgned() != highestPrioTask.getOverloadedExecTime())) {
					highestPrioTask = task;
					counter++;
				}
			}
		}*/
		
		
		return highestPrioTask;
	}

	/**
	 * Print the entire Tasks List
	 */
	public static void printTasksList() {
		ArrayList<Task> tasks = TasksList.getTasksList();
		for (Task task : tasks) {
			System.out.println("Task Name: " + task.getTaskName());
			System.out.println("Task Priority: " + task.getPriority());
			System.out.println("Task Criticalitiy: " + task.getCriticality());
			System.out.println("Zero Slack Instant: " + task.getZeroSlackInstant());
		}
	}

}
