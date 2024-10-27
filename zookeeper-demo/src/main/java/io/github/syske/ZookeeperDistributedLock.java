package io.github.syske;

import org.apache.zookeeper.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperDistributedLock implements Watcher {
    private static final String ZK_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String LOCK_ROOT = "/locks"; // 锁根节点
    private static final String LOCK_NAME = "/lock_"; // 锁节点名称前缀

    private ZooKeeper zk;
    private String currentLockPath; // 当前持有的锁路径
    private CountDownLatch lockLatch = new CountDownLatch(1);

    public ZookeeperDistributedLock() throws Exception {
        zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, this);
    }

    /**
     * 尝试获取锁
     * @return 如果成功获取锁则返回true，否则返回false创建一个临时顺序节点。
     * 获取锁根节点下的所有子节点，并按字典序排序。
     * 如果当前创建的节点是第一个节点，则表示成功获取锁。
     * 否则，对排在它前面的那个节点设置监听，等待该节点被删除。
     * 使用 synchronized 和 wait 来等待前一个节点被删除。
     *
     *
     */
    public boolean tryLock() throws Exception {
        lockLatch.await(); // 等待连接建立
        String path = zk.create(LOCK_ROOT + LOCK_NAME, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        currentLockPath = path;

        // 获取所有子节点并排序
        List<String> children = zk.getChildren(LOCK_ROOT, false);
        Collections.sort(children);

        // 判断当前节点是否是最小的
        if (path.endsWith(children.get(0))) {
            return true; // 成功获取锁
        } else {
            // 监听比自己小的最后一个节点
            String prevNode = children.get(Collections.binarySearch(children, path.substring(LOCK_ROOT.length() + 1)) - 1);
            zk.exists(LOCK_ROOT + "/" + prevNode, true); // 设置监听
            return false; // 没有获取到锁
        }
    }

    /**
     * 释放锁
     * 删除当前持有的锁节点，释放锁
     */
    public void releaseLock() throws Exception {
        if (currentLockPath != null && zk.exists(currentLockPath, false) != null) {
            zk.delete(currentLockPath, -1);
            currentLockPath = null;
        }
    }

    /**
     * 监听器回调
     * @param event 事件
     *  处理连接状态变化事件（SyncConnected）。
     * 处理节点删除事件（NodeDeleted），唤醒等待线程
     *
     */
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                notifyAll(); // 唤醒等待线程
            }
        } else if (event.getState() == Event.KeeperState.SyncConnected) {
            lockLatch.countDown();
        }
    }

    public static void main(String[] args) {
        try {
            ZookeeperDistributedLock lock = new ZookeeperDistributedLock();
            if (lock.tryLock()) {
                System.out.println("Lock acquired.");
                try {
                    // 执行业务逻辑
                    Thread.sleep(5000); // 模拟耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.releaseLock();
                    System.out.println("Lock released.");
                }
            } else {
                System.err.println("Failed to acquire lock.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}