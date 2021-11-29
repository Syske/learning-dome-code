package io.github.syske.thread;

/**
 * @program: thread-locksupport-demo
 * @description:
 * @author: syske
 * @date: 2021-11-29 13:13
 */
public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(this.getName() + ", " + this.getState());
        try {
            blockTest(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + ", " +this.getState());
    }

    public static synchronized void blockTest(Thread thread) throws InterruptedException {
        System.out.println(thread.getName() + ", 进入同步块");
        System.out.println(thread.getName() + ", " +thread.getState());
        Thread.sleep(50000);
        System.out.println(thread.getName() + ", 同步块执行完毕");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread("t1");
        MyThread myThread2 = new MyThread("t2");
        myThread.start();

        myThread2.start();
    }
}
