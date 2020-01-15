package com.hanggle.demo.zk;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class MyThread extends Thread {

    private CyclicBarrier cyclicBarrier;

    public MyThread(String name, CyclicBarrier cyclicBarrier) {
        super(name);
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "到达");
        try {
            cyclicBarrier.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        Lock lock = new MyZkLock("/testLock");
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "get lock1");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();

        System.out.println(Thread.currentThread().getName() + "wwww");
    }
}
