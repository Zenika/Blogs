package com.zenika.java7.forkjoin.fs;

import java.io.File;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FileSizeTask extends RecursiveTask<Long> {

	private File root;

	public FileSizeTask(File root) {
		this.root = root;
	}

	protected Long compute() {
		long size = 0;
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				ForkJoinTask<Long> subtask = new FileSizeTask(f).fork();
				size += subtask.join();
			} else {
				size += f.length();
			}
		}
		return size;
	}
}