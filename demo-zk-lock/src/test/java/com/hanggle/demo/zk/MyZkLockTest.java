package com.hanggle.demo.zk;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class MyZkLockTest {

    @Test
    public void lock() throws InterruptedException {


        Lock lock = new MyZkLock("/testLock");

        lock.lock();
        System.out.println("get lock2");

        TimeUnit.SECONDS.sleep(10);

        lock.unlock();
    }

    @Test
    public void lock2() throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        for (int i = 0; i < 7-1; i++) {
            new MyThread("t-" + i, cyclicBarrier).start();
        }
        TimeUnit.SECONDS.sleep(5);
        new MyThread("t-latest", cyclicBarrier).start();
    }

    @Test
    public void lock3() throws InterruptedException {
        int num = 0;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                System.out.println("go");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                count(num);
            }).start();
        }

        TimeUnit.SECONDS.sleep(300);
    }

    private void count(int i) {
        Lock lock = new MyZkLock("/testLock");

        lock.lock();
        System.out.println("get lock-" + i);
        i = i + 1;
        lock.unlock();

    }

    @Test
    public void lock34() throws InterruptedException {
        int num = 0;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                System.out.println("go");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                count2(num);
            }).start();
        }

        TimeUnit.SECONDS.sleep(300);
    }

    private void count2(int i) {
        Lock lock = new MyZkLock2("/testLock");

        lock.lock();
        System.out.println("get lock-" + i);
        i = i + 1;
        lock.unlock();

    }
}