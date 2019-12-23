package com.springboot.sspringboot.utils;


public class testUtil {
    public static void main(String[] args) {
        myThread myThread1 = new myThread("任务一");
        myThread myThread2 = new myThread("任务二");
        Thread thread1 = new Thread(myThread1);
        Thread thread2 = new Thread(myThread2);
        thread1.start();
        thread2.start();
    }
}

class myThread implements Runnable{
    private String taskName;

    public myThread(String taskName) {
        this.taskName = taskName;
    }
    synchronized public void doSomething() throws InterruptedException {
        String taskName1 = taskName+"处理后";
        System.out.println(taskName1);
        Thread.sleep(3000);
        System.out.println(taskName+"线程结束。。。");
    }


    @Override
    public void run() {
        try {
            doSomething();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


