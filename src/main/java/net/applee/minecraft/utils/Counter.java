package net.applee.minecraft.utils;

public class Counter {
	private long counts = 0;

	public static Counter of(int val) {
		Counter counter = new Counter();
		counter.counts = val;
		return counter;
	}

	public void increment() {
		counts++;
	}

	private void decrement() {
		counts--;
	}

	public void add(int val) {
		counts += val;
	}

	public void reset() {
		counts = 0;
	}

	public int getInt() {
		return (int) counts;
	}

	public long get() {
		return counts;
	}

	@Override
	public String toString() {
		return counts + "";
	}
}

