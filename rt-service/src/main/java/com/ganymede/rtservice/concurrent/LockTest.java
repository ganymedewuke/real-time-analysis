package com.ganymede.rtservice.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock(true);
		lock.lock();

		int i = 0;
		System.out.println(++i);

		lock.unlock();
	}
}
