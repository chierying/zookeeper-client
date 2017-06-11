package com.example.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by v_zhangbing on 2017/6/11.
 */
public class ZookeeperDemo {

    private static final String CONNECT_STRING = "192.168.40.128:2181";
    private ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        this.zooKeeper = new ZooKeeper(CONNECT_STRING, 2000, watchedEvent -> System.out.println(watchedEvent.getType()));
    }


    // 创建节点
    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        String node = zooKeeper.create("/idea", "this is idea data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("创建了:" + node);

    }

    // 获取子节点
    @Test
    public void testGetChildren() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/",event -> {
            System.out.println("监听到事件, 类型:"+event.getType()+", 路径:"+event.getPath());
        });
        children.forEach(s -> System.out.println("子节点:" + s));
    }
}
