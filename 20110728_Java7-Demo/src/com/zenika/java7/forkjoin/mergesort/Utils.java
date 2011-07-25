package com.zenika.java7.forkjoin.mergesort;

public class Utils {
	
	public static boolean isNotLargeEnough(int value) {
		return !isLargeEnough(value);
	}

	public static boolean isLargeEnough(int value) {
		return value >= 2;
	}
	
	public static void merge(int[] values, int first, int mid, int length) {
		int[] result = new int[length];
		int i = 0;

		int cursor1 = first;
		int cursor2 = mid;

		for (; cursor1 < mid && cursor2 < first + length;) {
			if (values[cursor1] < values[cursor2]) {
				result[i++] = values[cursor1++];
			} else {
				result[i++] = values[cursor2++];
			}
		}

		if (cursor1 == mid) {
			while (cursor2 < first + length) {
				result[i++] = values[cursor2++];
			}
		} else if (cursor2 == first + length) {
			while (cursor1 < mid) {
				result[i++] = values[cursor1++];
			}
		}

		// Replacement by sorted sequence.
		for (int j = 0; j < result.length; j++) {
			values[first + j] = result[j];
		}
	}

}
