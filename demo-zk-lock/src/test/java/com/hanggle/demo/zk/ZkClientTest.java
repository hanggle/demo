package com.hanggle.demo.zk;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ZkClientTest {

    @Test
    public void createEphemeral() throws InterruptedException {
        ZkClient zkClient = new ZkClient("192.168.111.7:2181");
        zkClient.createEphemeral("/test", "testdata");
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void createEphemeralSequential() throws InterruptedException {
        ZkClient zkClient = new ZkClient("192.168.111.7:2181");
        zkClient.createEphemeralSequential("/test", "testdata");
        zkClient.createEphemeralSequential("/test", "testdata");
        zkClient.createEphemeralSequential("/test", "testdata");
        TimeUnit.SECONDS.sleep(5);
    }

}
