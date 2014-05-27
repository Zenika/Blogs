package com.zenika.java7.forkjoin.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountDirectoriesTask extends RecursiveTask<Long> {

	private static final long serialVersionUID = 1L;

	private File root;

	public CountDirectoriesTask(File root) {
		this.root = root;
	}

	protected Long compute() {
		long size = 0;
		File[] files = root.listFiles();
		ArrayList<ForkJoinTask<Long>> subtasks = new ArrayList<ForkJoinTask<Long>>();
		for (File f : files) {
			if (f.isDirectory()) {
				subtasks.add(new CountDirectoriesTask(f).fork());
				++size;
			}
		}
		for (ForkJoinTask<Long> subtask : subtasks) {
			size += subtask.join();
		}
		return size;
	}
}