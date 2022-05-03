package com.example.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Zktest {


    @Test
    public void testZk() throws Exception {
        /**
         * 1.RetryUntilElapsed(int maxElapsedTimeMs, int sleepMsBetweenRetries)
         * 以sleepMsBetweenRetries的间隔重连,直到超过maxElapsedTimeMs的时间设置
         *
         * 2.RetryNTimes(int n, int sleepMsBetweenRetries)
         * 指定重连次数
         *
         * 3.RetryOneTime(int sleepMsBetweenRetry)
         * 重连一次,简单粗暴
         *
         * 4.ExponentialBackoffRetry
         * ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries)
         * ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries, int maxSleepMs)
         * 时间间隔 = baseSleepTimeMs * Math.max(1, random.nextInt(1 << (retryCount + 1)))
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,10);
        //创建客户端
        /**
         * 参数1：连接的ip地址和端口号
         * 参数2：会话超时时间，单位毫秒
         * 参数3：连接超时时间，单位毫秒
         * 参数4：失败重试策略
         */
        CuratorFramework client =  CuratorFrameworkFactory.newClient("192.168.123.127:2181",3000,1000,retryPolicy);
        //开启客户端(会阻塞到会话连接成功为止)
        client.start();
        System.out.println("连接成功");
        //1. 创建一个空节点(a)（只能创建一层节点）
        //测试发现默认会放客户端ip为默认值，除非自己指定节点的值
//        client.create().forPath("/a");

        //2. 创建一个有内容的b节点（只能创建一层节点）
//        client.create().forPath("/b", "这是b的内容".getBytes());

        //3. 创建多层节点
        // （creatingParentsIfNeeded）是否需要递归创建节点
        // withMode(CreateMode.PERSISTENT)  创建持久性 b节点
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/g/x");

        //4. 创建带有的序号的节点
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/e");

        //5. 创建临时节点（客户端关闭，节点消失）， 线程延迟10秒，可观察zk 里面的节点情况
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/f");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/ff", "this is ff".getBytes());

        //6. 创建临时带序号节点（客户端关闭，节点消失）， 线程延迟10秒，可观察zk 里面的节点情况
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/f");

        //获取某个节点的子节点.
//        System.out.println(client.getChildren().forPath("/g"));

        //获取某个节点的值.
//        byte[] x = client.getData().forPath("/g/x");
//        System.out.println(new String(x));

        Thread.sleep(10000);
        //关闭客户端
        client.close();
    }

}
