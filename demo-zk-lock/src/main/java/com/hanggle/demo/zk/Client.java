package com.hanggle.demo.zk;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("192.168.111.7:2181");
        zkClient.create("/test", "testdata", CreateMode.PERSISTENT);
    }


}
