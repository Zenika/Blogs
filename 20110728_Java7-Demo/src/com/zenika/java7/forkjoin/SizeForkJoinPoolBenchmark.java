package com.zenika.java7.forkjoin;

import java.util.concurrent.ForkJoinPool;
import com.zenika.java7.forkjoin.mergesort.ParrallelMergeSortAction;
import com.zenika.java7.forkjoin.mergesort.SequentialMergeSort;
import com.google.caliper.Param;
import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * Benchmark of {@link ParrallelMergeSortAction} where size of the thread pool is variable.<br/>
 * Note: Setup your JAVA_HOME_7 path in the {@link #main(String[])}.
 * 
 * @author bnouyrigat
 *
 */
public class SizeForkJoinPoolBenchmark {


	public static void main(String[] args) {
//		Runner.main(Benchmark.class, new String[]{"-Dsize=10,100,1000,10000,100000,1000000,10000000", "--timeUnit", "us", "-Dpool=2,3,4,7,9","--vm", "/home/bnouyrigat/Outils/jdk1.7.0/bin/java"});
		Runner.main(Benchmark.class, new String[]{"-Dsize=10", "--timeUnit", "us", "-Dpool=2,3", "--vm", "/home/bnouyrigat/Outils/jdk1.7.0/bin/java"});
	}

	private static int[] generateRandomSequence(int length) {
		int[] result = new int[length];
		for (int i = 0; i < length; ++i) {
			result[i] = (int) (Math.random() * 2 * length);
		}
		return result;
	}

	public static class Benchmark extends SimpleBenchmark {
		@Param
		int size; // set automatically by framework
		
		@Param
		int pool;

		private int[] reference; // set by us, in setUp()
		ParrallelMergeSortAction mergeAction;
		private ForkJoinPool forkJoinPool;

		@Override
		protected void setUp() {
			// @Param values are guaranteed to have been injected by now
			reference = generateRandomSequence(size);
			
			forkJoinPool = new ForkJoinPool(pool);
//			int[] workingCopy = Arrays.copyOf(reference, reference.length);
			mergeAction = new ParrallelMergeSortAction(reference, 0, reference.length);
		}

		public void timeParrallelMergeSortAction(int reps) {
			for (int i = 0; i < reps; i++) {
				forkJoinPool.invoke(mergeAction);
			}
		}
	}
}
