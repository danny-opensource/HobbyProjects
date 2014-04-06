package com.zsrm.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

import com.zsrm.datastructure.OSQueues;
import com.zsrm.datastructure.Task;
import com.zsrm.datastructure.TasksList;
import com.zsrm.preemption.TaskWorker;
import com.zsrm.utils.GeneralUtils;

/**
 * This is the main class visible to the user. User enters the tasks details via
 * InputSystem. The various attributes of the Tasks are assigned here and added
 * to the Tasks List
 * 
 * @author Janani V
 * 
 */
public class InputSystem {

	/**
	 * Read the Inputs from the user. If user wants to add multiple tasks, a "y"
	 * is typed by the user which recursively calls readInputs
	 */
	private void readInputs() {

		String taskName;
		int execTime;
		int overloadedExecTime;
		int period;
		String doAgain;

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the Task Name");
			taskName = br.readLine();
			System.out.println("Enter the Execution Time");
			execTime = Integer.parseInt(br.readLine());
			System.out.println("Enter the Overloaded Exec Time: ");
			overloadedExecTime = Integer.parseInt(br.readLine());
			System.out.println("Enter the Period");
			period = Integer.parseInt(br.readLine());
			createTask(taskName, execTime, overloadedExecTime, period);
			System.out.println("Do you want to add another task (y/n)");
			doAgain = br.readLine();
			if (doAgain.equalsIgnoreCase("Y")) {
				readInputs();
			} else {
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Create a new instance of the Task and add it to the TaskList.
	 * 
	 * @param taskName
	 * @param execTime
	 * @param overloadedExecTime
	 * @param period
	 */
	private void createTask(String taskName, int execTime, int overloadedExecTime, int period) {

		Task t = new Task(taskName, execTime, overloadedExecTime, period, 0, 0, 0, 0);
		TasksList.addTaskItem(t);
	}

	/**
	 * Once all the tasks are added to the list, call this method to compute the
	 * priorities and criticalities for the tasks
	 */
	private void assignTaskPrioritiesAndCriticalities() {
		ArrayList<Task> tasks = TasksList.getTasksList();

		int priority = 0;
		int criticality = tasks.size() - 1;

		Collections.sort(tasks);

		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			task.setPriority(priority);
			task.setCriticality(criticality);
			priority++;
			criticality--;
		}
	}

	private void addAllTasksToReadyQueue() {
		ArrayList<Task> tasksList = TasksList.getTasksList();
		Queue<Task> readyQueue = OSQueues.readyQueue;

		for (int i = 0; i < tasksList.size(); i++) {
			Task task = tasksList.get(i);
			readyQueue.add(task);
		}

		System.out.println("Printing the Ready Queue: " + OSQueues.readyQueue.toString());
	}

	private int lcmFind(int i, int y) {
		int n, x, s = 1, t = 1;
		for (n = 1;; n++) {
			s = i * n;
			for (x = 1; t < s; x++) {
				t = y * x;
			}
			if (s == t)
				break;
		}
		return (s);
	}

	/**
	 * Return the total LCM for all the Periods. Used to calculate the number of
	 * cycles to be shown for the tasks
	 * 
	 * @return lcm
	 */
	private int getPeriodLcm() {
		ArrayList<Task> tasksList = TasksList.getTasksList();

		int size = tasksList.size();
		int index = 0;
		int lcm = 0;
		lcm = lcmFind(tasksList.get(index).getPeriod(), tasksList.get(index++).getPeriod());
		System.out.println("LCM First: " + lcm);
		while (size != 1) {
			lcm = lcmFind(lcm, tasksList.get(index++).getPeriod());
			size--;
		}

		System.out.println("LCM: " + lcm);
		return lcm;
	}

	/**
	 * Main Method of exectution
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		InputSystem is = new InputSystem();
		is.readInputs();
		is.assignTaskPrioritiesAndCriticalities();
		GeneralUtils.updateZSI();
		GeneralUtils.printTasksList();
		is.addAllTasksToReadyQueue();
		OSQueues.runningQueue.add(OSQueues.readyQueue.poll());
		System.out.println("Printing Ready Queue Again after update: " + OSQueues.readyQueue.toString());
		System.out.println("Printing Running Queue: " + OSQueues.runningQueue.toString());
		TaskWorker worker = new TaskWorker();
		worker.startJob(is.getPeriodLcm());

	}

}
