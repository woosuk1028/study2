package com.study2.demo.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReservationLockManager {
    private final Map<Long, String> lockedPosts = new ConcurrentHashMap<>();

    public synchronized boolean tryLock(Long postId, String username) {
        if(lockedPosts.containsKey(postId)) {
            return lockedPosts.get(postId).equals(username);
        }
        lockedPosts.put(postId, username);
        return true;
    }

    public synchronized void unlock(Long postId, String username) {
        System.out.println("[UNLOCK] 요청 - postId: " + postId + ", 요청자: " + username);

        if(lockedPosts.containsKey(postId) && lockedPosts.get(postId).equals(username)) {
            lockedPosts.remove(postId);
        }
    }
}
