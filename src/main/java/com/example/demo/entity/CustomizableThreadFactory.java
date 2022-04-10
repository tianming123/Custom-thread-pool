package com.example.demo.entity;

import org.springframework.util.CustomizableThreadCreator;

import java.util.concurrent.ThreadFactory;

@SuppressWarnings("serial")
public class CustomizableThreadFactory extends CustomizableThreadCreator implements ThreadFactory {
    public CustomizableThreadFactory(){
        super();
    }

    public CustomizableThreadFactory(String name){
        super(name);
    }

    @Override
    public Thread newThread(Runnable r) {
        return createThread(r);
    }
}
