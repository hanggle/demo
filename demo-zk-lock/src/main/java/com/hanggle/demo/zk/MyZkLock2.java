package com.hanggle.demo.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collector;

/**
 * zookeeper公平锁
 * 无惊群效应
 */
public class MyZkLock2 implements Lock {
    private ZkClient zkClient;
    private String lockPath;
    private String currentPath;
    private String beforePath;
    public MyZkLock2(String lockPath) {
        super();
        this.lockPath = lockPath;
        zkClient = new ZkClient("192.168.111.7:2181");
        // 加锁前判断是否存在锁的永久节点，如果不存在则创建
        if (!zkClient.exists(lockPath)) {
            try {
                zkClient.createPersistent(lockPath);
            } catch (ZkNodeExistsException e) {
            }
        }
    }

    @Override
    public boolean tryLock() {
        // 当前路径是否为空，为空则创建
        if (currentPath == null) {
            currentPath = zkClient.createEphemeralSequential(lockPath + "/", "");
        }

        // 查找zookeeper中所有的节点
        List<String> children = zkClient.getChildren(lockPath);
        Collections.sort(children);

        //判断自己是否是第一个节点，是则加锁成功直接返回，不是则查找自己的上一个节点
        if (currentPath.endsWith(lockPath + "/" + children.get(0))) {
            return true;
        } else {
            // 查找自己的上一个节点并赋值
            int index = children.indexOf(currentPath.substring(lockPath.length() + 1));
            beforePath = lockPath + "/" + children.get(index - 1);
        }
        return false;
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            // 加锁失败则等待
            waitForLock();
            // 等待锁释放后再次尝试加锁
            lock();
        }
    }

    private void waitForLock() {
        // 自旋开关，监听到锁释放后开始抢占锁
        CountDownLatch countDownLatch = new CountDownLatch(1);
        IZkDataListener zkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                countDownLatch.countDown();
            }
        };
        // 监听上个节点
        zkClient.subscribeDataChanges(beforePath, zkDataListener);
        if (zkClient.exists(beforePath)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(beforePath, zkDataListener);
    }


    @Override
    public void unlock() {
        // 删除自己的节点，释放锁
        zkClient.delete(currentPath);
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
