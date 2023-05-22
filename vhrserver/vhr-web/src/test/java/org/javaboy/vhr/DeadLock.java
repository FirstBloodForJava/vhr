package org.javaboy.vhr;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    static {
        i = 1;
        //System.out.println(i);
        if (true) {
            System.out.println(Thread.currentThread().getName() + "init DeadLockClass ");

        }
    }
    static int i ;

    ReentrantLock lock1 = new ReentrantLock();
    ReentrantLock lock2 = new ReentrantLock();

    public void lock1(){
        System.out.println(Thread.currentThread().getName() + " 进入print方法");
        lock1.lock();
        if (Thread.currentThread().getName().contains("1")){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lock2.lock();

        lock1.unlock();
        lock2.unlock();
    }

    public void lock2(){
        System.out.println(Thread.currentThread().getName() + " 进入print方法");
        lock2.lock();
        if (Thread.currentThread().getName().contains("2")){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lock1.lock();

        lock2.unlock();
        lock1.unlock();
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Class<?> clazz = Class.forName("org.javaboy.vhr.ClassLoaderTest");

        /*final DeadLock demo = new DeadLock();
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始运行");
                demo.lock1();
                System.out.println(Thread.currentThread().getName() + "结束运行");
            }
        };
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始运行");
                demo.lock2();
                System.out.println(Thread.currentThread().getName() + "结束运行");
            }
        };

        Thread thread1 = new Thread(r1, "Thread 1");
        Thread thread2 = new Thread(r2, "Thread 2");

        thread1.start();
        thread2.start();*/
    }
}
