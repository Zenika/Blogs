package com.zenika.java7.forkjoin;

import java.io.File;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import com.zenika.java7.forkjoin.fs.CountDirectoriesTask;

public class Main {

	private final static DecimalFormat myFormatter = new DecimalFormat("##.##");

	private final static ForkJoinPool mainPool = new ForkJoinPool();

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		CountDirectoriesTask fst = new CountDirectoriesTask(new File("/home/lucien/doc"));
		Long nb = mainPool.invoke(fst);
		System.out.println("Il y a eu "
				+ mainPool.getStealCount()
				+ " tâches volées sur "
				+ nb
				+ " tâches forkées. ("
				+ myFormatter.format(Float.valueOf(100*mainPool.getStealCount())
						/ nb) + "%)");
	}

	public static int[] generateRandomSequence(int length) {
		int[] result = new int[length];
		for (int i = 0; i < length; ++i) {
			result[i] = (int) (Math.random() * 2 * length);
		}
		return result;
	}

}
