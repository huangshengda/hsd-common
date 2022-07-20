package com.hsd.util;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author huangshengda
 * @date 2022-07-18 17:10
 */
public class CompletableFutureUtil {

    public static <T, R> Map<T, R> callRetMap(Set<T> tSet, Function<T, R> function, int currThreshold, ExecutorService executorService, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        Map<T, R> map = new HashMap<>();
        if (CollectionUtils.isEmpty(tSet)) {
            return map;
        }
        if (tSet.size() < currThreshold) {
            tSet.forEach(item -> map.put(item, function.apply(item)));
        } else {
            List<CompletableFuture<?>> listFuture = new ArrayList<>();
            for (T t : tSet) {
                listFuture.add(CompletableFuture.runAsync(() -> map.put(t, function.apply(t)), executorService));
            }
            if (timeout <= 0) {
                CompletableFuture.allOf(listFuture.toArray(new CompletableFuture[0])).join();
            } else {
                CompletableFuture.allOf(listFuture.toArray(new CompletableFuture[0])).get(timeout, TimeUnit.SECONDS);
            }
        }
        return map;
    }

    public static <T, R> List<R> callRetList(Set<T> tSet, Function<T, R> function, int currThreshold, ExecutorService executorService, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        List<R> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(tSet)) {
            return list;
        }
        if (tSet.size() < currThreshold) {
            tSet.forEach(item -> list.add(function.apply(item)));
        } else {
            List<CompletableFuture<?>> listFuture = new ArrayList<>();
            for (T t : tSet) {
                listFuture.add(CompletableFuture.runAsync(() -> list.add(function.apply(t)), executorService));
            }
            if (timeout <= 0) {
                CompletableFuture.allOf(listFuture.toArray(new CompletableFuture[0])).join();
            } else {
                CompletableFuture.allOf(listFuture.toArray(new CompletableFuture[0])).get(timeout, TimeUnit.SECONDS);
            }
        }
        return list;
    }

    private CompletableFutureUtil() {
    }


}
