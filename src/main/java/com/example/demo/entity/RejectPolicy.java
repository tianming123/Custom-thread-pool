package com.example.demo.entity;
/**
 * 拒绝策略 【策略模式】
 * @param <T>
 */

@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockQueue<T> queue,T task);
}
