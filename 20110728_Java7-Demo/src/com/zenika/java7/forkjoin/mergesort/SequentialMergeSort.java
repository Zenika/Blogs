package com.zenika.java7.forkjoin.mergesort;

/**
 * @author Lucien Pereira
 */
public class SequentialMergeSort implements Runnable {

	private final int[] values;
	private final int first;
	private final int length;

	public SequentialMergeSort(int[] values, int first, int length) {
		this.values = values;
		this.first = first;
		this.length = length;
	}

	private void compute(int first, int length) {
		if (Utils.isNotLargeEnough(length)) {
			// NOp : An array with less than 2 elements is sorted (Right ?)
		} else {
			int midLength = length / 2;
			
			if (Utils.isLargeEnough(midLength)) {
				compute(first, midLength);
			}
			
			if (Utils.isLargeEnough(length - midLength)) {
				compute(first + midLength, length - midLength);
			}
			
			Utils.merge(values, first, first + midLength, length);
		}
	}

	@Override
	public void run() {
		compute(first, length);
	}
}
