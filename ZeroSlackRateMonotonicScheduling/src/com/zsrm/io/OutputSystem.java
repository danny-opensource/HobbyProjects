package com.zsrm.io;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.zsrm.datastructure.Task;

public class OutputSystem {

	private static LinkedHashMap ganttChart = new LinkedHashMap();

	public static void addToPrintList(Task task, int time) {

		ganttChart.put(time, task); // Adding the printable items to the map

	}

	public static void printGanttChart() {
		for (int i = 0; i < ganttChart.size(); i++) {
			System.out.print("---------");
		}
		System.out.print("-");
		System.out.println();
		for (int i = 0; i < ganttChart.size(); i++) {
			System.out.print("|   " + ((Task) ganttChart.get(i)).getTaskName() + "   ");

		}
		System.out.println("|");

		for (int i = 0; i < ganttChart.size(); i++) {
			System.out.print("---------");
		}
		System.out.print("-");
		System.out.println();

		for (int i = 0; i <= ganttChart.size(); i++) {
			if (i < 10) {
				System.out.print(i + "        ");
			} else {
				System.out.print(i + "       ");

			}
		}
		System.out.println();

		for (int i = 0; i < ganttChart.size(); i++) {
			System.out.print("---------");
		}
		System.out.print("-");
	}

}
