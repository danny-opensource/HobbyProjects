package com.zsrm.preemption;

import java.util.ArrayList;

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
				if (runningQTask.getCyclesAssisgned() == runningQTask.getOverloadedExecTime()) {
					OSQueues.readyQueue.add(runningQTask);
					ArrayList<Task> tasksList = TasksList.getTasksList();
					Task toBeExecTask = null;
					for (int i = 0; i < tasksList.size(); i++) {
						Task task = tasksList.get(i);
						if ((task.getZeroSlackInstant() <= timer) && (task.getZeroSlackInstant() != (task.getRound() * task.getPeriod()))
								&& (task.getCyclesAssisgned() != task.getOverloadedExecTime())) {
							if (toBeExecTask == null) {
								toBeExecTask = task;
							} else {
								if (task.getCriticality() < toBeExecTask.getCriticality()) {
									toBeExecTask = task;
								}
							}
						} else if (((task.getRound() * task.getPeriod()) == timer) && (task.getCyclesAssisgned() != task.getOverloadedExecTime())) {
							if ((task.getZeroSlackInstant()) == timer) {
								if (toBeExecTask == null) {
									toBeExecTask = task;
								} else {
									if (task.getCriticality() < toBeExecTask.getCriticality()) {
										toBeExecTask = task;
									}
								}
							} else {
								Task newTask = OSQueues.readyQueue.poll();
								if ((newTask.getPriority() < runningQTask.getPriority())) {
									toBeExecTask = newTask;
								} else {
									toBeExecTask = task;
								}
							}
							break;
						}

					}

					if (toBeExecTask == null) {
						OSQueues.runningQueue.add(OSQueues.readyQueue.poll());
					} else {
						OSQueues.runningQueue.add(toBeExecTask);
					}
					runningQTask.setCyclesAssisgned(0);
					ArrayList<Task> tList = TasksList.getTasksList();
					for (int i = 0; i < tList.size(); i++) {
						Task t1 = tList.get(i);
						if (timer == (t1.getRound() * t1.getPeriod())) {
							t1.setRound(t1.getRound() + 1);
							t1.setCyclesAssisgned(0);
						}
					}
				} else {
					ArrayList<Task> tasksList = TasksList.getTasksList();
					Task toBeExecTask = null;
					boolean zsiCheck = false;
					for (int i = 0; i < tasksList.size(); i++) {
						Task task = tasksList.get(i);
						if (((task.getRound() * task.getPeriod()) == timer)) {
							task.setCyclesAssisgned(0);
							if ((task.getZeroSlackInstant()) == timer) {
								if (toBeExecTask == null) {
									toBeExecTask = task;
								} else {
									if (task.getCriticality() < toBeExecTask.getCriticality()) {
										toBeExecTask = task;
									}
								}
							} else {

								ArrayList<Task> otherZsiTasks = TasksList.getTasksList();
								for (Task zsiTask : otherZsiTasks) {
									if (zsiTask != task) {
										if (((zsiTask.getRound()) * zsiTask.getPeriod())
												- (zsiTask.getOverloadedExecTime() - zsiTask.getCyclesAssisgned()) == timer) {
											zsiCheck = true;
											if (toBeExecTask != null) {
												if (zsiTask.getCriticality() < toBeExecTask.getCriticality()) {
													toBeExecTask = zsiTask;
												}
											} else {
												toBeExecTask = zsiTask;
											}
										}
									}
								}
								if (zsiCheck == false) {
									if (i < OSQueues.readyQueue.size()) {
										Task newTask = OSQueues.readyQueue.poll();
										if ((newTask.getPriority() < runningQTask.getPriority())) {
											toBeExecTask = newTask;
										} else {
											toBeExecTask = task;
										}
										OSQueues.readyQueue.add(newTask);
									}
								}
							}
							break;
						} else {
							if ((task.getZeroSlackInstant()) == timer && (task.getCyclesAssisgned() != 0)) {
								if (toBeExecTask == null) {
									toBeExecTask = task;
								} else {
									if (task.getCriticality() < toBeExecTask.getCriticality()) {
										toBeExecTask = task;
									}
								}
							} else {

								ArrayList<Task> otherZsiTasks = TasksList.getTasksList();
								for (Task zsiTask : otherZsiTasks) {
									if (zsiTask != task) {

										if (((zsiTask.getRound() * zsiTask.getPeriod()) - (zsiTask.getOverloadedExecTime() - zsiTask
												.getCyclesAssisgned())) == timer) {
											zsiCheck = true;
											if (toBeExecTask != null) {
												if (zsiTask.getCriticality() < toBeExecTask.getCriticality()) {
													toBeExecTask = zsiTask;
												}
											} else {
												toBeExecTask = zsiTask;
											}
										}
									}
								}

								if (zsiCheck == false) {
									if (i < OSQueues.readyQueue.size()) {
										Task newTask = OSQueues.readyQueue.poll();
										if ((newTask.getPriority() < runningQTask.getPriority()) && (newTask.getCyclesAssisgned() != 0)) {
											toBeExecTask = newTask;
										} else {
											toBeExecTask = runningQTask;
										}
										OSQueues.readyQueue.add(newTask);
									}
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

					}

					ArrayList<Task> tList = TasksList.getTasksList();
					for (int i = 0; i < tList.size(); i++) {
						Task t1 = tList.get(i);
						if (timer == (t1.getRound() * t1.getPeriod())) {
							t1.setRound(t1.getRound() + 1);
							t1.setCyclesAssisgned(0);
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
