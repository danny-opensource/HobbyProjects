package com.zsrm.io;

import com.zsrm.datastructure.Task;

public class OutputSystem {

	public static void print(Task task) {

		System.out.print(task.getTaskName() + "(" + task.getZeroSlackInstant()+")   ");

	}

}
