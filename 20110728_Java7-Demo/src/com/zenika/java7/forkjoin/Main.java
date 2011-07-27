package com.zenika.java7.forkjoin;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import com.zenika.java7.forkjoin.fs.CountDirectoriesTask;
import com.zenika.java7.forkjoin.mergesort.ParallelMergeSortAction;
import com.zenika.java7.forkjoin.mergesort.Utils;

public class Main {

	private final static DecimalFormat myFormatter = new DecimalFormat("##.##");

	private final static ForkJoinPool mainPool = new ForkJoinPool();

	private static void mergeSortMain(int size) {
		int[] data = Utils.generateRandomSequence(size);
		ParallelMergeSortAction mergeAction = new ParallelMergeSortAction(data,
				0, data.length);

		System.out.println(Arrays.toString(data));
		mainPool.invoke(mergeAction);
		System.out.println(Arrays.toString(data));
	}

	private static void fsMain(String path) {
		CountDirectoriesTask fst = new CountDirectoriesTask(new File(
				"/home/lucien/doc"));
		Long nb = mainPool.invoke(fst);
		System.out.println("Il y a eu "
				+ mainPool.getStealCount()
				+ " tâches volées sur "
				+ nb
				+ " tâches forkées. ("
				+ myFormatter.format(Float.valueOf(100 * mainPool
						.getStealCount()) / nb) + "%)");
	}

	public static void main(String[] args) {
		mergeSortMain(1000);
		// fsMain("C:\\");
	}

}
