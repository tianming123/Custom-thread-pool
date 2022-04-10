package com.example.demo.custom;

public class CustomThread {

    public static void main(String[] args) {
        new Thread(new Runnable() {@Override
        public void run() {
            System.out.println("Custom Run");
            System.out.println(Thread.currentThread().getName());
        }
        },"custom-thread-1").start();
    }

}
