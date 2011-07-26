package com.zenika.java7.forkjoin.mergesort;

import java.util.concurrent.RecursiveAction;

public class ParallelMergeSortAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private final int[] values;
	private final int first;
	private final int length;

	public ParallelMergeSortAction(int[] values, int first, int length) {
		this.values = values;
		this.first = first;
		this.length = length;
	}

	@Override
	protected void compute() {
		if (length < 2) {
			// NOp : An array with less than 2 elements is sorted (Right ?)
		} else {
			int midLength = length / 2;

			ParallelMergeSortAction m1 = null;
			if (Utils.isLargeEnough(midLength)) {
				m1 = new ParallelMergeSortAction(values, first, midLength);
				m1.fork();
			}

			if (Utils.isNotLargeEnough(length - midLength)) {
				ParallelMergeSortAction m2 = new ParallelMergeSortAction(
						values, first + midLength, length - midLength);
				m2.compute();
			}

			if (null != m1) { // Did I needed to fork ?
				m1.join();
			}
			Utils.merge(values, first, first + midLength, length);
		}
	}

}
