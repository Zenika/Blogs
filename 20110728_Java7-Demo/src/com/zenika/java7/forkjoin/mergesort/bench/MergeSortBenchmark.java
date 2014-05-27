package com.zenika.java7.forkjoin.mergesort.bench;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;
import com.zenika.java7.forkjoin.mergesort.ParallelMergeSortAction;
import com.zenika.java7.forkjoin.mergesort.SequentialMergeSort;
import com.zenika.java7.forkjoin.mergesort.Utils;

/**
 * @author bnouyrigat
 * @author Lucien Pereira
 */
public class MergeSortBenchmark extends SimpleBenchmark {

	private static final int size = 10000000;
	private static final ExecutorService singleThreadPool = Executors
			.newSingleThreadExecutor();
	private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
	
	private static int[] reference;
	private static SequentialMergeSort sequentialMergeSort;
	private static ParallelMergeSortAction mergeAction;

	@Override
	protected void setUp() throws Exception {
		reference = Utils.generateRandomSequence(size);
	}

	public void timeSequentialMergeSort(int reps) throws Exception {
		for (int i = 0; i < reps; i++) {
			int[] workingCopy = Arrays.copyOf(reference, reference.length);
			sequentialMergeSort = new SequentialMergeSort(workingCopy, 0,
					workingCopy.length);

			Future<?> future = singleThreadPool.submit(sequentialMergeSort);
			future.get();
		}
	}

	public void timeParrallelMergeSortAction(int reps) {
		for (int i = 0; i < reps; i++) {
			int[] workingCopy = Arrays.copyOf(reference, reference.length);
			mergeAction = new ParallelMergeSortAction(workingCopy, 0,
					workingCopy.length);

			forkJoinPool.invoke(mergeAction);
		}
	}

	public static void main(String[] args) {
		Runner.main(MergeSortBenchmark.class, new String[] { "--trials", "2",
				"--timeUnit", "ns", "--vm",
				"/home/lucien/Téléchargements/jdk1.7.0/bin/java" });
	}

}
