package com.hanggle.demo.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * zookeeper非公平锁
 * 有惊群效应
 */
public class MyZkLock implements Lock {
    private ZkClient zkClient;
    private String lockPath;
    public MyZkLock(String path) {
        super();
        this.lockPath = path;
        zkClient = new ZkClient("192.168.111.7:2181");
    }
    @Override
    public void lock() {
        if (!tryLock()) {
            waitForLock();
            lock();
        }
    }

    private void waitForLock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        IZkDataListener zkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("=======节点被删除========");
                countDownLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(lockPath, zkDataListener);
        System.out.println(zkClient.exists(lockPath));
        if (zkClient.exists(lockPath)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(lockPath, zkDataListener);
    }

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(lockPath);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    @Override
    public void unlock() {
        System.out.println("unlock");
        zkClient.delete(lockPath);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }
}
