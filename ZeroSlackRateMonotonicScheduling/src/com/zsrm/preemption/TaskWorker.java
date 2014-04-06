package com.zsrm.preemption;

import java.util.ArrayList;
import java.util.Collections;

import com.zsrm.datastructure.OSQueues;
import com.zsrm.datastructure.Task;
import com.zsrm.datastructure.TasksList;
import com.zsrm.io.OutputSystem;
import com.zsrm.utils.GeneralUtils;

public class TaskWorker {

	private int timer = 0;

	public void startJob(int periodLCM) {
		while (periodLCM != 0) {
			try {
				Task runningQTask = OSQueues.runningQueue.poll();
				OutputSystem.print(runningQTask);
				runningQTask.setCyclesAssisgned(runningQTask.getCyclesAssisgned() + 1);
				timer++;
				Thread.sleep(1000);

				GeneralUtils.updateZSI();
				/*
				 * System.out.println("** PRINTING LIST START *** ");
				 * GeneralUtils.printTasksList();
				 * System.out.println("** PRINTING LIST END *** ");
				 */

				if (runningQTask.getCyclesAssisgned() == runningQTask.getOverloadedExecTime()) {
					OSQueues.readyQueue.add(runningQTask);
					/*
					 * if (timer == (runningQTask.getRound() *
					 * runningQTask.getPeriod())) {
					 * runningQTask.setRound(runningQTask.getRound() + 1); }
					 */

					ArrayList<Task> tasksList = TasksList.getTasksList();
					Task toBeExecTask = null;
					for (int i = 0; i < tasksList.size(); i++) {
						Task task = tasksList.get(i);
						if ((task.getZeroSlackInstant() == timer) && (task.getZeroSlackInstant() != task.getPeriod())) {
							if (toBeExecTask == null) {
								toBeExecTask = task;
							} else {
								if (task.getCriticality() < toBeExecTask.getCriticality()) {
									toBeExecTask = task;
								}
							}
						}
					}

					if (toBeExecTask == null) {
						OSQueues.runningQueue.add(OSQueues.readyQueue.poll());
					} else {
						OSQueues.runningQueue.add(toBeExecTask);
					}

					runningQTask.setCyclesAssisgned(0);

				} else {
					ArrayList<Task> tasksList = TasksList.getTasksList();
					Task toBeExecTask = null;
					for (int i = 0; i < tasksList.size(); i++) {
						Task task = tasksList.get(i);
						if (((task.getRound() * task.getPeriod()) == timer)) {
							if ((task.getZeroSlackInstant()) == timer) {
								if (toBeExecTask == null) {
									toBeExecTask = task;
								} else {
									if (task.getCriticality() < toBeExecTask.getCriticality()) {
										toBeExecTask = task;
									}
								}
							} else {
								// toBeExecTask =
								// GeneralUtils.getHighestPriorityTask();

								Task newTask = OSQueues.readyQueue.peek();
								if ((newTask.getPriority() < runningQTask.getPriority())) {
									toBeExecTask = newTask;
								} else {
									toBeExecTask = runningQTask;
								}

							}
							break;
						} else {
							if ((task.getZeroSlackInstant()) == timer) {
								if (toBeExecTask == null) {
									toBeExecTask = task;
								} else {
									if (task.getCriticality() < toBeExecTask.getCriticality()) {
										toBeExecTask = task;
									}
								}
							} else {
								Task newTask = OSQueues.readyQueue.peek();
								if ((newTask.getPriority() < runningQTask.getPriority()) && (newTask.getCyclesAssisgned() != 0)) {
									toBeExecTask = newTask;
								} else {
									toBeExecTask = runningQTask;
								}
							}
							continue;
						}

					}
					if (toBeExecTask == null) {

						OSQueues.runningQueue.add(runningQTask);
					} else {
						OSQueues.readyQueue.remove(toBeExecTask);
						OSQueues.readyQueue.add(runningQTask);
						OSQueues.runningQueue.add(toBeExecTask);

						/*
						 * if ((timer % toBeExecTask.getPeriod()) == 0) {
						 * OSQueues.runningQueue.add(toBeExecTask);
						 * OSQueues.readyQueue.remove(toBeExecTask); // Sub the
						 * remaining time here before adding to // ready q
						 * toBeExecTask.setCyclesAssisgned(0);
						 * OSQueues.readyQueue.add(runningQTask); } else {
						 * OSQueues.runningQueue.add(toBeExecTask); }
						 */
					}

					ArrayList<Task> tList = TasksList.getTasksList();
					for (int i = 0; i < tList.size(); i++) {
						Task t1 = tList.get(i);
						if (timer == (t1.getRound() * t1.getPeriod()) && (t1.getCyclesAssisgned() == 0)) {
							t1.setRound(t1.getRound() + 1);
						}
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			periodLCM--;
		}
	}

}
