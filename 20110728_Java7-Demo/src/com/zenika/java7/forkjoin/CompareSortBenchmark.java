package com.zenika.java7.forkjoin;

import static com.zenika.java7.forkjoin.Main.generateRandomSequence;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import com.zenika.java7.forkjoin.mergesort.ParrallelMergeSortAction;
import com.zenika.java7.forkjoin.mergesort.SequentialMergeSort;

import com.google.caliper.Param;
import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * Benchmark to compare {@link SequentialMergeSort} vs {@link ParrallelMergeSortAction}.<br/>
 * Note: Setup your JAVA_HOME_7 path in the {@link #main(String[])}.
 * 
 * @author bnouyrigat
 *
 */
public class CompareSortBenchmark extends SimpleBenchmark {

	@Param
	int size; // set automatically by framework

	private int[] reference; // set by us, in setUp()
	SequentialMergeSort sequentialMergeSort;
	ParrallelMergeSortAction mergeAction;
	private final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();;
	private final ForkJoinPool forkJoinPool = new ForkJoinPool(5);

	@Override
	protected void setUp() {
		// @Param values are guaranteed to have been injected by now
		reference = generateRandomSequence(size);
		
		int[] workingCopy = Arrays.copyOf(reference, reference.length);
		sequentialMergeSort = new SequentialMergeSort(workingCopy, 0, workingCopy.length);
		
		workingCopy = Arrays.copyOf(reference, reference.length);
		mergeAction = new ParrallelMergeSortAction(workingCopy, 0, workingCopy.length);
	}

	public void timeSequentialMergeSort(int reps) throws Exception {
		for (int i = 0; i < reps; i++) {
			Future<?> future = singleThreadPool.submit(sequentialMergeSort);
			future.get();
		}
	}

	public void timeParrallelMergeSortAction(int reps) {
		for (int i = 0; i < reps; i++) {
			forkJoinPool.invoke(mergeAction);
		}
	}

	/**
	 * Main entry to execute the benchmark.<br/>
	 * Note: Setup your JAVA_HOME_7 path in the args of {@link Runner#main(String...)}.
	 */
	public static void main(String[] args) {
//		Runner.main(Benchmark.class, new String[]{"-Dsize=10,100,1000,10000,100000,1000000,10000000","--vm", "/home/bnouyrigat/Outils/jdk1.7.0/bin/java"});
		Runner.main(CompareSortBenchmark.class, new String[]{"-Dsize=10", "--timeUnit", "us", "--vm", "/home/bnouyrigat/Outils/jdk1.7.0/bin/java"});
	}

}
