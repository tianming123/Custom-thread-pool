package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        List<String> list = new ArrayList<>(4);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.parallelStream().forEach(string -> {
            string = string + "paralleStream";
            System.out.println(Thread.currentThread().getName()+":-> "+string);
        });
    }

}
